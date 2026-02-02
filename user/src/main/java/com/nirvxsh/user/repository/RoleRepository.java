package com.nirvxsh.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nirvxsh.user.entity.Role;

public interface RoleRepository
        extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT r FROM Role r WHERE r.roleName IN :roleNameList")
    List<Role> findByRoleNameList(List<String> roleNameList);
}
