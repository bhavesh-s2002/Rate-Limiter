package com.bhavesh.rate_limiter.model;

public class TokenBucket {

    private final int capacity;
    private final double refillRate;

    private double tokens;
    private long lastRefillTime;

    public TokenBucket(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {

        refill();

        if (tokens >= 1) {
            tokens--;
            return true;
        }

        return false;
    }

    private void refill() {

        long now = System.currentTimeMillis();

        double tokensToAdd =
                (now - lastRefillTime) / 1000.0 * refillRate;

        tokens = Math.min(capacity, tokens + tokensToAdd);

        lastRefillTime = now;
    }
}