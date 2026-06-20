package com.springBoot.RateGuard.factory;

import com.springBoot.RateGuard.limiter.RateLimiter;
import com.springBoot.RateGuard.strategy.RateLimiterType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateLimiterFactory {

    private final List<RateLimiter>limiters;

    public RateLimiterFactory(List<RateLimiter>limiters){
        this.limiters=limiters;
    }

    public RateLimiter getLimiter(RateLimiterType type){

        return limiters
                .stream()
                .filter(
                        limiter -> limiter.getType() == type
                )
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Limiter not found"
                        )
                );
    }
}
