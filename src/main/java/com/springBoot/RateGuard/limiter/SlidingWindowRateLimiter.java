package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.config.RateLimitConfig;
import com.springBoot.RateGuard.model.SlidingWindowEntry;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter implements RateLimiter {
    private final RateLimitStore<SlidingWindowEntry> store;

    private final RateLimitConfig config;

    public SlidingWindowRateLimiter(RateLimitStore<SlidingWindowEntry> store, RateLimitConfig config){
        this.store = store;
        this.config = config;
    }

    @Override
    public boolean allowRequest(String clientId) {

        long currentTime = System.currentTimeMillis();

        SlidingWindowEntry entry = store.get(clientId);

        if (entry == null) {
            store.save(
                    clientId,
                    new SlidingWindowEntry(
                            1,
                            0,
                            currentTime
                    )
            );
            return true;
        }
        long elapsed = currentTime - entry.getWindowStartTime();

        if (elapsed >= config.getSlidingWindow().getWindowSize()) {
            boolean resetPrevious = elapsed >= config.getSlidingWindow().getWindowSize() * 2;

            entry.moveWindow(currentTime,resetPrevious);
            elapsed = 0;
        }
        double overlap = (double)
                (config.getSlidingWindow().getWindowSize() - elapsed) / config.getSlidingWindow().getWindowSize();

        double estimatedCount = entry.getPreviousCount() * overlap
                + entry.getCurrentCount();

        if (estimatedCount >= config.getSlidingWindow().getMaxRequests()) {
            return false;
        }
        entry.incrementCurrent();

        store.save(clientId, entry);
        return true;
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.SLIDING_WINDOW;
    }
}
