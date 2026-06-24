package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.model.TokenBucketEntry;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RedisTokenBucketStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class TokenBucketRateLimiter implements RateLimiter {
    private final RedisTokenBucketStore store;

    public TokenBucketRateLimiter(RedisTokenBucketStore store){
        this.store = store;
    }

    @Override
    public RateLimitResult allowRequest(String clientId, RateLimitPolicy policy) {

        long currentTime = System.currentTimeMillis();

        TokenBucketEntry bucket = store.get(clientId);
        System.out.println(
                "Client = " + clientId
        );
        System.out.println(
                "Tokens before = " +
                        (bucket == null ? "null" : bucket.getTokens())
        );
        if (bucket == null) {
            bucket = new TokenBucketEntry(
                    policy.getCapacity()-1,
                    currentTime
            );

            store.save(
                    clientId, bucket
            );

            return new RateLimitResult(
                    true,
                    policy.getCapacity(),
                    (long) bucket.getTokens()
            );
        }

        refill(bucket, currentTime, policy);

        if (bucket.getTokens() >= 1) {
            bucket.consumeToken();
            store.save(clientId,bucket);
            System.out.println(
                    "Tokens after = " + bucket.getTokens()
            );
            return new RateLimitResult(
                    true,
                    policy.getCapacity(),
                    (long) bucket.getTokens()
            );
        }

        return new RateLimitResult(
                false,
                policy.getCapacity(),
                0
        );
    }

    private void refill(TokenBucketEntry bucket, long currentTime, RateLimitPolicy policy){

        long elapsedTime = currentTime - bucket.getLastRefillTime();

        long tokensToAdd =
                (elapsedTime / 1000) * policy.getRefillRate();

        long newTokens =
                Math.min(
                        policy.getCapacity(), bucket.getTokens() + tokensToAdd
                );

        bucket.addTokens(
                newTokens - bucket.getTokens()
        );

        bucket.updateRefillTime(
                currentTime
        );
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.TOKEN_BUCKET;
    }

}
