package com.springBoot.RateGuard.model;

public class TokenBucketEntry {

    private long tokens;

    private long lastRefillTime;

    public TokenBucketEntry(long tokens, long lastRefillTime){
        this.tokens = tokens;
        this.lastRefillTime = lastRefillTime;
    }
    public long getTokens(){
        return tokens;
    }

    public long getLastRefillTime(){
        return lastRefillTime;
    }

    public void addTokens(long amount){
        tokens += amount;
    }

    public void consumeToken(){
        tokens--;
    }

    public void updateRefillTime(long time){
        this.lastRefillTime=time;
    }
}
