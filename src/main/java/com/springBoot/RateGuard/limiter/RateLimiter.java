package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.strategy.RateLimiterType;

public interface RateLimiter {

    boolean allowRequest(
            String clientId,
            RateLimitPolicy policy
    );

    RateLimiterType getType();

}
