package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.factory.RateLimiterFactory;
import com.springBoot.RateGuard.limiter.RateLimiter;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final RateLimiterFactory factory;

    public RateLimiterServiceImpl(RateLimiterFactory factory){
        this.factory = factory;
    }

    @Override
    public boolean allowRequest(String clientId){
        RateLimiter limiter=
                factory.getLimiter(
                        RateLimiterType.TOKEN_BUCKET
                );
        return limiter.allowRequest(clientId);
    }
}
