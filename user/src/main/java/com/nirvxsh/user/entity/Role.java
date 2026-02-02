package com.nirvxsh.user.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "role_name", unique = true)
    private String roleName;

    private List<String> permissionCode;

    private String status;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
