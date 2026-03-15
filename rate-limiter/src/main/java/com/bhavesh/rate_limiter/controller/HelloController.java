package com.bhavesh.rate_limiter.controller;

import com.bhavesh.rate_limiter.service.TokenBucketRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    TokenBucketRateLimiter limiter;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(
            @RequestParam String user) {

        if (!limiter.allowRequest(user)) {
            return ResponseEntity
                    .status(429)
                    .body("Too many requests");
        }

        return ResponseEntity.ok("Hello " + user);
    }
}
