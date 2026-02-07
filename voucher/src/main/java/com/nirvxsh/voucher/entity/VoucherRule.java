package com.nirvxsh.voucher.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class VoucherRule {

    private Integer discountValue;   // 10, 100
    private String discountUnit;      // PERCENT, BAHT

    private String productCategory;   // ELECTRONICS, FOOD
    private String productSku;        // optional

    private Integer minOrderAmount;

}
