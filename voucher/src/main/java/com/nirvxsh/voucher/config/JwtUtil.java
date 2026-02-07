package com.nirvxsh.voucher.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void debugJwt() {
        System.out.println("SECRET=[" + secret + "]");
        System.out.println("LENGTH=" + secret.length());
    }

    private Key getSignKey() {
         return Keys.hmacShaKeyFor(
            secret.trim().getBytes(StandardCharsets.UTF_8)
    );
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    public List<String> extractPermissions(String token) {
        return parseClaims(token)
                .get("permissions", List.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

