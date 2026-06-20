package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.config.RateLimitConfig;
import com.springBoot.RateGuard.model.TokenBucketEntry;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketRateLimiter implements RateLimiter {
    private final RateLimitStore store;
    private final RateLimitConfig config;

    public TokenBucketRateLimiter(RateLimitStore store,RateLimitConfig config){
        this.store = store;
        this.config=config;
    }

    @Override
    public boolean allowRequest(String clientId) {

        long currentTime = System.currentTimeMillis();

        TokenBucketEntry bucket = (TokenBucketEntry) store.get(clientId);

        if (bucket == null) {
            bucket = new TokenBucketEntry(
                    config.getBucketCapacity() - 1,
                    currentTime
            );

            store.save(
                    clientId, bucket
            );

            return true;
        }

        refill(bucket, currentTime);

        if (bucket.getTokens() >= 1) {
            bucket.consumeToken();
            return true;
        }

        return false;
    }

    private void refill(TokenBucketEntry bucket, long currentTime){

        long elapsedTime = currentTime - bucket.getLastRefillTime();

        double tokensToAdd =
                (elapsedTime / 1000.0) * config.getRefillRate();

        double newTokens =
                Math.min(
                        config.getBucketCapacity(), bucket.getTokens() + tokensToAdd
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
