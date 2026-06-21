package com.springBoot.RateGuard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitConfig {

    private FixedWindow fixedWindow = new FixedWindow();

    private TokenBucket tokenBucket = new TokenBucket();

    private SlidingWindow slidingWindow = new SlidingWindow();


    public FixedWindow getFixedWindow(){
        return fixedWindow;
    }

    public TokenBucket getTokenBucket(){
        return tokenBucket;
    }

    public SlidingWindow getSlidingWindow(){
        return slidingWindow;
    }


    public static class FixedWindow {

        private int maxRequests;
        private long windowSize;

        public int getMaxRequests(){
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests){
            this.maxRequests=maxRequests;
        }

        public long getWindowSize(){
            return windowSize;
        }

        public void setWindowSize(long windowSize){
            this.windowSize=windowSize;
        }
    }


    public static class TokenBucket {

        private double capacity;
        private double refillRate;

        public double getCapacity(){
            return capacity;
        }

        public void setCapacity(double capacity){
            this.capacity=capacity;
        }

        public double getRefillRate(){
            return refillRate;
        }

        public void setRefillRate(double refillRate){
            this.refillRate=refillRate;
        }
    }


    public static class SlidingWindow {

        private int maxRequests;
        private long windowSize;

        public int getMaxRequests(){
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests){
            this.maxRequests=maxRequests;
        }

        public long getWindowSize(){
            return windowSize;
        }

        public void setWindowSize(long windowSize){
            this.windowSize=windowSize;
        }
    }

}