package com.nirvxsh.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import com.nirvxsh.user.entity.User;

@Entity
@Data
public class RefreshToken {

    @Id
    @Column(name = "token_id")
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String token;

    private String userId;

    private LocalDateTime expireDate;
    
}
