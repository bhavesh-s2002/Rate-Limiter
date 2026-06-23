package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.TokenBucketEntry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketStore {

    private final ConcurrentHashMap<String, TokenBucketEntry> store
            = new ConcurrentHashMap<>();


    public TokenBucketEntry get(String clientId){
        return store.get(clientId);
    }

    public void save(
            String clientId,
            TokenBucketEntry entry
    ){
        store.put(clientId,entry);
    }
}
