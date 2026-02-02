package com.nirvxsh.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nirvxsh.user.config.CustomUserDetails;
import com.nirvxsh.user.config.JwtUtil;
import com.nirvxsh.user.dto.req.LoginRequest;
import com.nirvxsh.user.dto.req.RegisterRequest;
import com.nirvxsh.user.dto.res.AuthResponse;
import com.nirvxsh.user.entity.RefreshToken;
import com.nirvxsh.user.entity.User;
import com.nirvxsh.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse login(LoginRequest req) {
         Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        // User user = userRepository
        //         .findByUsernameOrEmail(req.getIdentifier(), req.getIdentifier())
        //         .orElseThrow(() -> new RuntimeException("User not found"));

        // if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
        //     throw new RuntimeException("Invalid password");
        // }

        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken());
    }

    @Transactional
    public void register(RegisterRequest req) {

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setStatus("ACTIVE");
        user.setRoles(List.of("USER"));
        user.setCreatedBy("SYSTEM");
        user.setUpdatedBy("SYSTEM");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public AuthResponse refresh(String refreshToken) {

        RefreshToken token = refreshTokenService.verify(refreshToken);
        User user = userRepository.findById(token.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateToken(user);
        RefreshToken newRefreshToken = refreshTokenService.create(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken.getToken());
    }

}
