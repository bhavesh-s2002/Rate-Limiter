package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Client;
import com.springBoot.RateGuard.factory.RateLimiterFactory;
import com.springBoot.RateGuard.limiter.RateLimiter;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.strategy.RateLimiterType;
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
    public boolean allowRequest(String clientId){
        Client client =
                clientService.getClient(clientId);

        RateLimitPolicy policy =
                policyService.getPolicy(
                        client.getPlan()
                );

        RateLimiter limiter=
                factory.getLimiter(
                        policy.getType()
                );
        return limiter.allowRequest(clientId,policy);
    }
}
