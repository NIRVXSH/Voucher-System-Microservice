package com.nirvxsh.voucher.entity;

import java.time.LocalDateTime;

import com.nirvxsh.voucher.utils.AppUtils.VoucherStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "vouchers",
    uniqueConstraints = @UniqueConstraint(columnNames = "code")
)
@Getter
@NoArgsConstructor
public class Voucher {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherStatus status;

    @Column(name = "assigned_user_id")
    private String assignedUserId;

    @Column(nullable = false)
    private LocalDateTime expiredDate;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private String updatedBy;

    @Version
    private Long version;


    public boolean isExpired() {
        return expiredDate.isBefore(LocalDateTime.now());
    }

    public void assignTo(String userId) {
        this.assignedUserId = userId;
    }

    public void redeem() {
        this.status = VoucherStatus.REDEEMED;
    }

    public static Voucher create(
            String code,
            LocalDateTime expiredAt
    ) {
        Voucher voucher = new Voucher();
        voucher.code = code;
        voucher.status = VoucherStatus.ACTIVE;
        voucher.expiredDate = expiredAt;
        return voucher;
    }
}