package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.strategy.RateLimiterType;

public interface RateLimiter {

    boolean allowRequest(String clientId);
    RateLimiterType getType();
}
