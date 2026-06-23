package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.strategy.RateLimiterType;

public interface RateLimiter {

    RateLimitResult allowRequest(
            String clientId,
            RateLimitPolicy policy
    );

    RateLimiterType getType();

}
