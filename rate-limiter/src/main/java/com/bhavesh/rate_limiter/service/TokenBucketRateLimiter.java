package com.bhavesh.rate_limiter.service;

import com.bhavesh.rate_limiter.model.TokenBucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBucketRateLimiter {

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    private static final int CAPACITY = 10;
    private static final double REFILL_RATE = 1.0;

    public boolean allowRequest(String userId) {

        buckets.putIfAbsent(userId,
                new TokenBucket(CAPACITY, REFILL_RATE));

        TokenBucket bucket = buckets.get(userId);

        return bucket.tryConsume();
    }
}
