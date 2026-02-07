package com.nirvxsh.user.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.nirvxsh.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private int expiration;


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

    public String generateToken(User user, List<String> permissions) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(
                extractClaims(token).getSubject());
    }

    public String extractUsername(String token) {
        return extractClaims(token).get("username").toString();
    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {

        try {
            extractClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}