package com.springBoot.RateGuard.model;

import java.io.Serializable;

public class SlidingWindowEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private int currentCount;

    private int previousCount;

    private long windowStartTime;

    public SlidingWindowEntry(int currentCount, int previousCount, long windowStartTime){
        this.currentCount = currentCount;
        this.previousCount = previousCount;
        this.windowStartTime = windowStartTime;
    }

    public int getCurrentCount(){
        return currentCount;
    }

    public int getPreviousCount(){
        return previousCount;
    }

    public long getWindowStartTime(){
        return windowStartTime;
    }

    public void incrementCurrent(){
        currentCount++;
    }

    public void moveWindow(long newStart, boolean resetPrevious){
        if(resetPrevious){
            previousCount = 0;
        }
        else{
            previousCount = currentCount;
        }
        currentCount = 0;
        windowStartTime = newStart;
    }

}
