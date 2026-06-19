package com.springBoot.RateGuard.controller;

import com.springBoot.RateGuard.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(){
        return "API Working";
    }

}
