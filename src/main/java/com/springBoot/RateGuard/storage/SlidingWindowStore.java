package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.SlidingWindowEntry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlidingWindowStore {

    private final ConcurrentHashMap<String, SlidingWindowEntry> store =
            new ConcurrentHashMap<>();

    public SlidingWindowEntry get(String clientId){

        return store.get(clientId);

    }

    public void save(
            String clientId,
            SlidingWindowEntry entry
    ){

        store.put(
                clientId,
                entry
        );

    }

}
