package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.SlidingWindowEntry;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisSlidingWindowStore {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisSlidingWindowStore(
            RedisTemplate<String,Object> redisTemplate
    ){
        this.redisTemplate = redisTemplate;
    }

    private String key(String clientId){

        return "sliding_window:" + clientId;

    }

    public SlidingWindowEntry get(
            String clientId
    ){

        return (SlidingWindowEntry)
                redisTemplate.opsForValue()
                        .get(key(clientId));

    }

    public void save(
            String clientId,
            SlidingWindowEntry entry
    ){

        redisTemplate.opsForValue()
                .set(
                        key(clientId),
                        entry,
                        Duration.ofMinutes(2)
                );

    }

}
