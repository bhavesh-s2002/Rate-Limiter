package com.springBoot.RateGuard.service;

public interface RateLimiterService {

    boolean allowRequest(String clientId);
}
