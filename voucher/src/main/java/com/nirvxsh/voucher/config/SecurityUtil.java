package com.nirvxsh.voucher.config;

import java.util.UUID;


import org.springframework.stereotype.Component;

import com.nirvxsh.voucher.exception.UnauthorizedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class SecurityUtil {

    public String getCurrentUserId() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException();
        }

        return (String) auth.getPrincipal();
    }

    public boolean hasPermission(String permission) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(permission));
    }
}
