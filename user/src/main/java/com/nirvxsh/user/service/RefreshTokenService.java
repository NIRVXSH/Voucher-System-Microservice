package com.nirvxsh.user.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nirvxsh.user.entity.RefreshToken;
import com.nirvxsh.user.entity.User;
import com.nirvxsh.user.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public RefreshToken create(User  user) {

        repo.deleteByUserId(user.getId().toString());

        RefreshToken token = new RefreshToken();
        token.setUserId(user.getId().toString());
        token.setToken(UUID.randomUUID().toString());
        token.setExpireDate(LocalDateTime.now().plusSeconds(refreshExpiration));

        return repo.save(token);
    }

    @Transactional
    public RefreshToken verify(String token) {

        RefreshToken refreshToken = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpireDate().isBefore(LocalDateTime.now())) {
            repo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
