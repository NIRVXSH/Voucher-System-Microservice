
package com.nirvxsh.voucher.dto.req;

import java.time.LocalDateTime;

import com.nirvxsh.voucher.entity.VoucherRule;
import com.nirvxsh.voucher.utils.AppUtils.VoucherType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoucherRequest {

    @NotBlank
    private String voucherCode;

    @NotBlank
    private String voucherName;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    private Integer totalQuota;

    @NotNull
    private VoucherType type;

    private VoucherRule rule;
}
