package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.SlidingWindowEntry;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter implements RateLimiter {
    private final RateLimitStore<SlidingWindowEntry> store;

    public SlidingWindowRateLimiter(RateLimitStore<SlidingWindowEntry> store){
        this.store = store;
    }

    @Override
    public boolean allowRequest(String clientId, RateLimitPolicy policy) {

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

        if (elapsed >= policy.getWindowSize()) {
            boolean resetPrevious = elapsed >= policy.getWindowSize() * 2;

            entry.moveWindow(currentTime,resetPrevious);
            elapsed = 0;
        }
        double overlap = (double)
                (policy.getWindowSize() - elapsed) / policy.getWindowSize();

        double estimatedCount = entry.getPreviousCount() * overlap
                + entry.getCurrentCount();

        if (estimatedCount >= policy.getMaxRequests()) {
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
