package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Plan;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    public RateLimitPolicy getPolicy(Plan plan){

        if(plan == Plan.PREMIUM){
            return new RateLimitPolicy(
                    RateLimiterType.TOKEN_BUCKET,
                    100,
                    60000,
                    100,
                    10
            );
        }

        return new RateLimitPolicy(
                RateLimiterType.FIXED_WINDOW,
                5,
                60000,
                0,0
        );
    }

}
