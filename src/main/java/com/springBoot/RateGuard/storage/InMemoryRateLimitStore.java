package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimitStore implements RateLimitStore {
    private final ConcurrentHashMap<String, RateLimitEntry> store = new ConcurrentHashMap<>();

    @Override
    public RateLimitEntry get(String clientId){
        return store.get(clientId);
    }

    @Override
    public void save(String clientId, RateLimitEntry entry){
        store.put(clientId, entry);
    }
}
