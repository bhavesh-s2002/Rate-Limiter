package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.config.RateLimitConfig;
import com.springBoot.RateGuard.model.RateLimitEntry;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final RateLimitStore store;
    private final RateLimitConfig config;

    public FixedWindowRateLimiter(RateLimitStore store, RateLimitConfig config){
        this.store=store; this.config=config;
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.FIXED_WINDOW;
    }

    @Override
    public boolean allowRequest(String clientId){
        RateLimitEntry entry =
                (RateLimitEntry) store.get(clientId);
        long currentTime = System.currentTimeMillis();
        if(entry == null){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return true;
        }
        if(currentTime - entry.getWindowStartTime() > config.getWindowSize()){
            store.save(clientId,
                    new RateLimitEntry(1, currentTime)
            );
            return true;
        }
        if(entry.getRequestCount() < config.getMaxRequests()){
            entry.increment();
            return true;
        }
        return false;
    }
}
