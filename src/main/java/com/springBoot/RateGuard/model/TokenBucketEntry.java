package com.springBoot.RateGuard.model;

public class TokenBucketEntry {

    private double tokens;

    private long lastRefillTime;

    public TokenBucketEntry(double tokens, long lastRefillTime){
        this.tokens = tokens;
        this.lastRefillTime = lastRefillTime;
    }
    public double getTokens(){
        return tokens;
    }

    public long getLastRefillTime(){
        return lastRefillTime;
    }

    public void addTokens(double amount){
        tokens += amount;
    }

    public void consumeToken(){
        tokens--;
    }

    public void updateRefillTime(long time){
        this.lastRefillTime=time;
    }
}
