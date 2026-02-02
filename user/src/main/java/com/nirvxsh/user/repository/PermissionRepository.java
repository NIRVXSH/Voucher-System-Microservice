package com.nirvxsh.user.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nirvxsh.user.entity.Permission;

public interface PermissionRepository
        extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByPermissionName(String permissionName);

    @Query("SELECT p FROM Permission p WHERE p.permissionCode IN :codes")
    List<Permission> findByPermissionCodeIn(List<String> codes);
}

