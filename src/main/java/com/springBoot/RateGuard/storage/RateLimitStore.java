package com.springBoot.RateGuard.storage;

public interface RateLimitStore<T> {
    T get(String clientId);

    void save(String clientId, T value);

}
