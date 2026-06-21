package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.config.RateLimitConfig;
import com.springBoot.RateGuard.model.TokenBucketEntry;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketRateLimiter implements RateLimiter {
    private final RateLimitStore<TokenBucketEntry> store;

    public TokenBucketRateLimiter(RateLimitStore<TokenBucketEntry> store){
        this.store = store;
    }

    @Override
    public boolean allowRequest(String clientId, RateLimitPolicy policy) {

        long currentTime = System.currentTimeMillis();

        TokenBucketEntry bucket = store.get(clientId);

        if (bucket == null) {
            bucket = new TokenBucketEntry(
                    policy.getCapacity()-1,
                    currentTime
            );

            store.save(
                    clientId, bucket
            );

            return true;
        }

        refill(bucket, currentTime, policy);

        if (bucket.getTokens() >= 1) {
            bucket.consumeToken();
            return true;
        }

        return false;
    }

    private void refill(TokenBucketEntry bucket, long currentTime, RateLimitPolicy policy){

        long elapsedTime = currentTime - bucket.getLastRefillTime();

        double tokensToAdd =
                (elapsedTime / 1000.0) * policy.getRefillRate();

        double newTokens =
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
