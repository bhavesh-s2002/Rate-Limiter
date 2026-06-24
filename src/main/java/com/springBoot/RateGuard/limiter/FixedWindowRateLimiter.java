package com.springBoot.RateGuard.limiter;

import com.springBoot.RateGuard.model.RateLimitEntry;
import com.springBoot.RateGuard.model.RateLimitResult;
import com.springBoot.RateGuard.policy.RateLimitPolicy;
import com.springBoot.RateGuard.storage.RedisFixedWindowStore;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final RedisFixedWindowStore store;

    public FixedWindowRateLimiter(RedisFixedWindowStore store) {
        this.store = store;
    }

    @Override
    public RateLimiterType getType() {
        return RateLimiterType.FIXED_WINDOW;
    }

    @Override
    public RateLimitResult allowRequest(
            String clientId,
            RateLimitPolicy policy
    ) {

        RateLimitEntry entry = store.get(clientId);

        long currentTime = System.currentTimeMillis();

        System.out.println(
                "ENTRY = " + entry
        );
        if (entry != null) {
            System.out.println(
                    "COUNT = " + entry.getRequestCount()
            );
        }
        // first request
        if (entry == null) {

            RateLimitEntry newEntry =
                    new RateLimitEntry(
                            1,
                            currentTime
                    );

            store.save(
                    clientId,
                    newEntry
            );

            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests() - 1
            );
        }

        // window expired
        if (currentTime - entry.getWindowStartTime()
                > policy.getWindowSize()) {


            RateLimitEntry newEntry =
                    new RateLimitEntry(
                            1,
                            currentTime
                    );

            store.save(
                    clientId,
                    newEntry
            );

            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests() - 1
            );
        }

        // request allowed
        if (entry.getRequestCount()
                < policy.getMaxRequests()) {

            entry.increment();

            // IMPORTANT FOR REDIS
            store.save(
                    clientId,
                    entry
            );

            return new RateLimitResult(
                    true,
                    policy.getMaxRequests(),
                    policy.getMaxRequests()
                            - entry.getRequestCount()
            );
        }
        // blocked
        return new RateLimitResult(
                false,
                policy.getMaxRequests(),
                0
        );
    }
}
