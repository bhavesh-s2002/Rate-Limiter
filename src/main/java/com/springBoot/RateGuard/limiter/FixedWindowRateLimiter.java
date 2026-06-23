package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitEntry;
import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.FixedWindowStore;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final FixedWindowStore store;

    public FixedWindowRateLimiter(FixedWindowStore store){
        this.store=store;
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.FIXED_WINDOW;
    }

    @Override
    public RateLimitResult allowRequest(String clientId,
                                        RateLimitPolicy policy){
        RateLimitEntry entry = store.get(clientId);
        long currentTime = System.currentTimeMillis();
        if(entry == null){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests()
                            - entry.getRequestCount()
            );
        }
        if(currentTime - entry.getWindowStartTime() > policy.getWindowSize()){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests()
                            - entry.getRequestCount()
            );
        }
        if(entry.getRequestCount() < policy.getMaxRequests()){
            entry.increment();
            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests()
                            - entry.getRequestCount()
            );
        }
        return new RateLimitResult(
                false,
                policy.getMaxRequests(),
                0
        );
    }
}
