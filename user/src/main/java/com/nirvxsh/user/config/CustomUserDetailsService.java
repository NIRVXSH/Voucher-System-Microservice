package com.nirvxsh.user.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nirvxsh.user.entity.Role;
import com.nirvxsh.user.entity.User;
import com.nirvxsh.user.repository.PermissionRepository;
import com.nirvxsh.user.repository.RoleRepository;
import com.nirvxsh.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository
                .findByUsernameOrEmail(username, username)
                .orElseThrow();
       // ทำ Logic ดึง Permissions ที่นี่
        List<String> allPermissionCodes = roleRepository.findByRoleNameList(user.getRoles())
                .stream()
                .flatMap(role -> role.getPermissionCode().stream())
                .distinct()
                .toList();

        List<SimpleGrantedAuthority> authorities = permissionRepository.findByPermissionCodeIn(allPermissionCodes)
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermissionName()))
                .toList();

        return new CustomUserDetails(user ,authorities);
    }
}
