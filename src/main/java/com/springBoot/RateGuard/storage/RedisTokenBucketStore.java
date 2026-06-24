package com.springBoot.RateGuard.storage;

import com.springBoot.RateGuard.model.TokenBucketEntry;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisTokenBucketStore {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisTokenBucketStore(
            RedisTemplate<String,Object> redisTemplate
    ){
        this.redisTemplate = redisTemplate;
    }

    private String getKey(String clientId){

        return "token_bucket:" + clientId;

    }

    public TokenBucketEntry get(
            String clientId
    ){

        return (TokenBucketEntry)
                redisTemplate.opsForValue()
                        .get(
                                getKey(clientId)
                        );

    }

    public void save(
            String clientId,
            TokenBucketEntry entry
    ){
        redisTemplate.opsForValue()
                .set(
                        getKey(clientId),
                        entry,
                        Duration.ofHours(1)
                );

    }
}
