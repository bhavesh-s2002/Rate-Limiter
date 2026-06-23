package com.springBoot.RateGuard.exception;

public class RateLimitExceededException
        extends RuntimeException {

    public RateLimitExceededException(
            String message
    ){
        super(message);
    }
}
