package com.springBoot.RateGuard.service;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    @Override
    public boolean allowRequest(String clientId){
        return true;
    }
}
