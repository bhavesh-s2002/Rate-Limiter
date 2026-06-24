package com.springBoot.RateGuard.model;

import java.io.Serializable;

public class RateLimitEntry implements Serializable {
    private static final long serialVersionUID = 1L;

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
