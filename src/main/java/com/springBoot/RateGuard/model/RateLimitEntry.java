package com.springBoot.RateGuard.model;

public class RateLimitEntry {
    private int requestCount;

    private long windowStartTime;

    public RateLimitEntry(int requestCount, long windowStartTime){
        this.requestCount=requestCount;
        this.windowStartTime=windowStartTime;
    }

    public int getRequestCount(){
        return requestCount;
    }

    public long getWindowStartTime() {
        return windowStartTime;
    }

    public void increment(){
        requestCount++;
    }
}
