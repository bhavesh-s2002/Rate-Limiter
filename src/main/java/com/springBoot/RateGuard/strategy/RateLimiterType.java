package com.springBoot.RateGuard.strategy;

public enum RateLimiterType {
    FIXED_WINDOW,
    TOKEN_BUCKET,
    SLIDING_WINDOW
}
