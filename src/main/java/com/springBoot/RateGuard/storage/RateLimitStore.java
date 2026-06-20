package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;

public interface RateLimitStore {
    Object get(String clientId);


    void save(String clientId, Object value);

}
