package com.springBoot.RateGuard.config;

import org.springframework.stereotype.Component;

@Component
public class RateLimitConfig {
    public int getMaxRequests(){
        return 10;
    }

    public long getWindowSize(){
        return 60000;
    }

    public double getBucketCapacity(){
        return 10;
    }


    public double getRefillRate(){
        return 1;
    }
}
