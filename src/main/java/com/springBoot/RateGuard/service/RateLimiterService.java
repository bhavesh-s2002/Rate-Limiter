package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.model.RateLimitResult;

public interface RateLimiterService {

    RateLimitResult allowRequest(String clientId);
}
