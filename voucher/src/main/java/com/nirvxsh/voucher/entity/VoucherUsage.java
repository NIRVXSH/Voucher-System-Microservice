package com.nirvxsh.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
   name = "voucher_usages",
   uniqueConstraints = {
     @UniqueConstraint(columnNames = {"voucher_id", "user_id"})
   }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherUsage {

    @Id
    @GeneratedValue
    private UUID voucherUsageId;

    @Column(nullable = false)
    private UUID voucherId;

    @Column(nullable = false)
    private String userId;

    private LocalDateTime usedDateTime;

    private String status;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public VoucherUsage(UUID voucherId, String userId, LocalDateTime usedDateTime) {
        this.voucherId = voucherId;
        this.userId = userId;
        this.usedDateTime = usedDateTime;
    }
}
