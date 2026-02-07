package com.nirvxsh.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nirvxsh.user.dto.req.LoginRequest;
import com.nirvxsh.user.dto.req.RegisterRequest;
import com.nirvxsh.user.dto.res.AuthResponse;
import com.nirvxsh.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        authService.register(req);
    }

    // @PostMapping("/refresh")
    // public AuthResponse refresh(@RequestParam String refreshToken) {
    //     return authService.refresh(refreshToken);
    // }
}
