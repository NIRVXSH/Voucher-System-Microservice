package com.nirvxsh.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {
    @GetMapping("/check")
    public String healthCheck() {
        return "Server is running";
    }
    
}
