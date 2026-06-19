package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;

public interface RateLimitStore {
    RateLimitEntry get(String clientId);


    void save(String clientId, RateLimitEntry entry);

}
