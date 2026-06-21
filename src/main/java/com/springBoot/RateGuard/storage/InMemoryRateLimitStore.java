package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimitStore<T> implements RateLimitStore<T> {
    private final ConcurrentHashMap<String, T> store = new ConcurrentHashMap<>();

    @Override
    public T get(String clientId){
        return store.get(clientId);
    }

    @Override
    public void save(String clientId, T value){
        store.put(clientId, value);
    }
}
