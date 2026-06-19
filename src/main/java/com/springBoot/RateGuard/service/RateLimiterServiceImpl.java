package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.limiter.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterServiceImpl(RateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean allowRequest(String clientId){
        return rateLimiter.allowRequest(clientId);
    }
}
