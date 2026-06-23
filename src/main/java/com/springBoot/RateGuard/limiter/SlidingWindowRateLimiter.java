package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.model.SlidingWindowEntry;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RateLimitStore;
import com.springBoot.RateGuard.storage.SlidingWindowStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter implements RateLimiter {
    private final SlidingWindowStore store;

    public SlidingWindowRateLimiter(SlidingWindowStore store){
        this.store = store;
    }

    @Override
    public RateLimitResult allowRequest(String clientId, RateLimitPolicy policy) {

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
            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    0
            );
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
            return new RateLimitResult(
                    false,
                    policy.getMaxRequests(),
                    0
            );
        }
        entry.incrementCurrent();

        store.save(clientId, entry);
        return new RateLimitResult(
                true,
                policy.getMaxRequests(),
                0
        );
    }

    @Override
    public RateLimiterType getType(){
        return RateLimiterType.SLIDING_WINDOW;
    }
}
