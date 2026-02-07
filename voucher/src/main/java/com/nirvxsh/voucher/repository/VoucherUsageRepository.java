package com.nirvxsh.voucher.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nirvxsh.voucher.entity.VoucherUsage;

public interface VoucherUsageRepository extends JpaRepository<VoucherUsage, UUID>{
    boolean existsByVoucherIdAndUserId(UUID voucherId, String userId);

}
