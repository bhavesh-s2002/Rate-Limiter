package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitEntry;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final RateLimitStore<RateLimitEntry> store;

    public FixedWindowRateLimiter(RateLimitStore<RateLimitEntry> store){
        this.store=store;
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.FIXED_WINDOW;
    }

    @Override
    public boolean allowRequest(String clientId,
                                RateLimitPolicy policy){
        RateLimitEntry entry = store.get(clientId);
        long currentTime = System.currentTimeMillis();
        if(entry == null){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return true;
        }
        if(currentTime - entry.getWindowStartTime() > policy.getWindowSize()){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return true;
        }
        if(entry.getRequestCount() < policy.getMaxRequests()){
            entry.increment();
            return true;
        }
        return false;
    }
}
