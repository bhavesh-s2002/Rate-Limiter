package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.RateLimitEntry;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisFixedWindowStore {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisFixedWindowStore(
            RedisTemplate<String,Object> redisTemplate
    ){
        this.redisTemplate = redisTemplate;
    }

    private String key(String clientId){

        return "fixed_window:" + clientId;

    }

    public RateLimitEntry get(String clientId){
        Object value =
                redisTemplate.opsForValue()
                        .get(key(clientId));

        return (RateLimitEntry) value;

    }

    public void save(
            String clientId,
            RateLimitEntry entry
    ){

        redisTemplate.opsForValue()
                .set(
                        key(clientId),
                        entry,
                        Duration.ofMinutes(2)
                );

    }

}
