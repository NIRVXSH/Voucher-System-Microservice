package com.nirvxsh.voucher.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nirvxsh.voucher.config.SecurityUtil;
import com.nirvxsh.voucher.dto.req.VoucherRequest;
import com.nirvxsh.voucher.entity.Voucher;
import com.nirvxsh.voucher.entity.VoucherUsage;
import com.nirvxsh.voucher.exception.BusinessException;
import com.nirvxsh.voucher.repository.VoucherRepository;
import com.nirvxsh.voucher.repository.VoucherUsageRepository;
import com.nirvxsh.voucher.utils.AppUtils.ErrorCode;

import jakarta.transaction.Transactional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherUsageRepository usageRepository;

    @Autowired
    private SecurityUtil securityUtil;


    @PreAuthorize("hasAuthority('VOUCHER_REDEEM')")
    @Transactional
    public void redeem(String code) throws Exception {

        String userId = securityUtil.getCurrentUserId();

        Voucher voucher = voucherRepository
                .findByVoucherCodeAndStatus(code,"ACTIVE")
                .orElse(null);

        if (voucher == null) {
            throw new NotFoundException();
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(voucher.getStartDateTime())
                || now.isAfter(voucher.getEndDateTime())) {
            throw new BusinessException(ErrorCode.VOUCHER_EXPIRED);
        }

        boolean used = usageRepository.existsByVoucherIdAndUserId(
                voucher.getVoucherId(), userId);

        if (used) {
            throw new BusinessException(ErrorCode.VOUCHER_ALREADY_USED);
        }

        if (voucher.getTotalQuota() != null
                && voucher.getUsedCount() >= voucher.getTotalQuota()) {
            throw new BusinessException(ErrorCode.VOUCHER_OUT_OF_QUOTA);
        }

        // save usage
        usageRepository.save(
                new VoucherUsage(voucher.getVoucherId(), userId, now));

        voucher.setUsedCount(voucher.getUsedCount() + 1);
    }




     @PreAuthorize("hasAuthority('Create Voucher')")
    @Transactional
    public UUID createVoucher(VoucherRequest req) {

        if (voucherRepository.existsByVoucherCode(req.getVoucherCode())) {
            throw new BusinessException(ErrorCode.VOUCHER_CODE_ALREADY_EXISTS);
        }

        if (req.getEndDateTime().isBefore(req.getStartDateTime())) {
            throw new BusinessException(ErrorCode.INVALID_VOUCHER_PERIOD);
        }

        String userId = securityUtil.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        Voucher voucher = new Voucher();
        voucher.setVoucherCode(req.getVoucherCode());
        voucher.setVoucherName(req.getVoucherName());
        voucher.setDescription(req.getDescription());
        voucher.setStartDateTime(req.getStartDateTime());
        voucher.setEndDateTime(req.getEndDateTime());
        voucher.setTotalQuota(req.getTotalQuota());
        voucher.setUsedCount(0);
        voucher.setType(req.getType());
        voucher.setRule(req.getRule());
        voucher.setStatus("ACTIVE");
        voucher.setCreatedBy(userId.toString());
        voucher.setCreatedDate(now);

        voucherRepository.save(voucher);

        return voucher.getVoucherId();
    }

}
