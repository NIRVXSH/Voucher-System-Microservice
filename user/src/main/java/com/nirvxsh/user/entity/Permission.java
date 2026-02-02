package com.nirvxsh.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Permission {

    @Id
    @Column(name = "permission_id")
    @GeneratedValue 
    private UUID permission_id;

    @Column(name="permission_code")
    private String permissionCode;

    @Column(name="permission_name")
    private String permissionName;

    @Column(name="status")
    private String status;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
