package com.nirvxsh.user.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nirvxsh.user.entity.Permission;
import com.nirvxsh.user.entity.Role;
import com.nirvxsh.user.entity.User;
import com.nirvxsh.user.repository.PermissionRepository;
import com.nirvxsh.user.repository.RoleRepository;

public class CustomUserDetails implements UserDetails {

    private User user;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user ,Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    
}
