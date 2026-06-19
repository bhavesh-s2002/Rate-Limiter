package com.springBoot.RateGuard.limiter;

public interface RateLimiter {

    boolean allowRequest(String clientId);
}
