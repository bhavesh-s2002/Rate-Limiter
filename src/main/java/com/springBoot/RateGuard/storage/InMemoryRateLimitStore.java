package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimitStore implements RateLimitStore {
    private final ConcurrentHashMap<String, Object> store = new ConcurrentHashMap<>();

    @Override
    public Object get(String clientId){
        return store.get(clientId);
    }

    @Override
    public void save(String clientId, Object value){
        store.put(clientId, value);
    }
}
