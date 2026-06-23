package com.springBoot.RateGuard.policy;

import com.springBoot.RateGuard.strategy.RateLimiterType;

public class RateLimitPolicy {

    private RateLimiterType type;

    private int maxRequests;

    private long windowSize;

    private long capacity;

    private long refillRate;

    public RateLimitPolicy(
            RateLimiterType type,
            int maxRequests,
            long windowSize,
            long capacity,
            long refillRate
    ){
        this.type = type;
        this.maxRequests = maxRequests;
        this.windowSize = windowSize;
        this.capacity = capacity;
        this.refillRate = refillRate;
    }

    public RateLimiterType getType(){
        return type;
    }

    public int getMaxRequests(){
        return maxRequests;
    }

    public long getWindowSize(){
        return windowSize;
    }

    public long getCapacity(){
        return capacity;
    }

    public long getRefillRate(){
        return refillRate;
    }

}
