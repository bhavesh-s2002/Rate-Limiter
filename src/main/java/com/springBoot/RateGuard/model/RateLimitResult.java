package com.springBoot.RateGuard.model;

public class RateLimitResult {
    private boolean allowed;

    private long limit;

    private long remaining;

    public RateLimitResult(
            boolean allowed,
            long limit,
            long remaining
    ){
        this.allowed = allowed;
        this.limit = limit;
        this.remaining = remaining;
    }

    public boolean isAllowed(){
        return allowed;
    }

    public long getLimit(){
        return limit;
    }

    public long getRemaining(){
        return remaining;
    }
}
