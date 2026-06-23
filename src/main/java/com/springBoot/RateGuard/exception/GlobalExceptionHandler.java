package com.springBoot.RateGuard.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            RateLimitExceededException.class
    )
    public void handleRateLimit(HttpServletResponse response)
            throws Exception{
        response.setStatus(
                HttpStatus.TOO_MANY_REQUESTS.value()
        );

        response.getWriter()
                .write(
                        "Too many requests"
                );
    }
}
