package com.nirvxsh.voucher.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nirvxsh.voucher.dto.req.VoucherRequest;
import com.nirvxsh.voucher.dto.response.VoucherResponse;
import com.nirvxsh.voucher.service.VoucherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
@Tag(name = "Voucher", description = "Voucher operations")
public class VoucherController {

    private final VoucherService voucherService;

    @Operation(
            summary = "Redeem voucher",
            description = "Redeem a voucher by voucher code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voucher redeemed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
            @ApiResponse(responseCode = "400", description = "Voucher invalid or expired")
    })
    @PostMapping("/{code}/redeem")
    public ResponseEntity<Void> redeemVoucher(
            @PathVariable("code") String code
    ) throws Exception {

        voucherService.redeem(code);
        return ResponseEntity.ok().build();
    }



        @Operation(summary = "Create voucher")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voucher created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(
            @Valid @RequestBody VoucherRequest request
    ) {

        UUID voucherId = voucherService.createVoucher(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new VoucherResponse(voucherId));
    }
}
