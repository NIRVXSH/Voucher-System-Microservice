package com.nirvxsh.voucher.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nirvxsh.voucher.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, UUID>{

    @Query("SELECT v FROM Voucher v WHERE v.voucherCode = :code AND v.status = :status")
    Optional<Voucher> findByVoucherCodeAndStatus(String code ,String status);

    boolean existsByVoucherCode(String voucherCode);
}
