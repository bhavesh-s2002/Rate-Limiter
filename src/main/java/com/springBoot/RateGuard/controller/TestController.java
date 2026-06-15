package com.springBoot.RateGuard.controller;

import com.springBoot.RateGuard.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final RateLimiterService rateLimiterService;

    public TestController(RateLimiterService rateLimiterService){
        this.rateLimiterService=rateLimiterService;
    }

    @GetMapping("/api/test")
    public String test(){
        boolean allowed= rateLimiterService.allowRequest("user1");

        if(allowed){
            return "Request allowed";
        }
        return "Request blocked";
    }

}
