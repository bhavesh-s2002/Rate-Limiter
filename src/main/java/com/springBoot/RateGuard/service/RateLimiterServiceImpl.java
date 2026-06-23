package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Client;
import com.springBoot.RateGuard.exception.RateLimitExceededException;
import com.springBoot.RateGuard.factory.RateLimiterFactory;
import com.springBoot.RateGuard.limiter.RateLimiter;
import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final ClientService clientService;

    private final PolicyService policyService;

    private final RateLimiterFactory factory;

    public RateLimiterServiceImpl(RateLimiterFactory factory,ClientService clientService,
                                  PolicyService policyService){
        this.factory = factory;
        this.clientService = clientService;
        this.policyService = policyService;

    }

    @Override
    public RateLimitResult allowRequest(String clientId){
        Client client =
                clientService.getClient(clientId);

        RateLimitPolicy policy =
                policyService.getPolicy(
                        client.getPlan()
                );
        System.out.println(
                "Selected algorithm = "
                        + policy.getType()
        );

        RateLimiter limiter=
                factory.getLimiter(
                        policy.getType()
                );
        RateLimitResult result =
                limiter.allowRequest(
                        clientId,
                        policy
                );
        if(!result.isAllowed()){
            throw new RateLimitExceededException(
                    "Rate limit exceeded. Try again later."
            );
        }
        return result;
    }
}
