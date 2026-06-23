package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Plan;
import com.springBoot.RateGuard.entity.PolicyEntity;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.repository.PolicyRepository;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    private final PolicyRepository repository;

    public PolicyService(PolicyRepository repository){
        this.repository = repository;
    }
    public RateLimitPolicy getPolicy(Plan plan){

        PolicyEntity entity =
                repository.findByPlan(plan.name());

        if(entity == null){
            throw new RuntimeException(
                    "Policy not found"
            );
        }

        return new RateLimitPolicy(
                RateLimiterType.valueOf(
                        entity.getAlgorithm()
                ),

                entity.getMaxRequests(),

                entity.getWindowSize(),

                entity.getCapacity(),

                entity.getRefillRate()
        );
    }

}
