package com.nirvxsh.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.nirvxsh.voucher.utils.AppUtils.VoucherType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Table(name = "vouchers")
@Data
public class Voucher {

    @Id
    @GeneratedValue
    private UUID voucherId;

    @Column(unique = true, nullable = false)
    private String voucherCode;

    private String voucherName;
    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Integer totalQuota;        // ทั้งระบบ (optional)

    private Integer usedCount = 0;

    @Enumerated(EnumType.STRING)
    private VoucherType type; // DISCOUNT, FREE_SHIPPING, CASHBACK

    @Embedded
    private VoucherRule rule;

    private String status;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
