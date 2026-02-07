package com.nirvxsh.voucher.utils;

import org.springframework.http.HttpStatus;

public class AppUtils {
    public enum VoucherStatus {
        ACTIVE,
        REDEEMED,
        EXPIRED,
        CANCELLED
    }

    public enum VoucherType {
        DISCOUNT,
        FREE_SHIPPING,
        CASHBACK
    }

    public enum ErrorCode {

    VOUCHER_NOT_FOUND("VOUCHER_404", HttpStatus.NOT_FOUND),
    VOUCHER_EXPIRED("VOUCHER_410", HttpStatus.GONE),
    VOUCHER_ALREADY_USED("VOUCHER_409", HttpStatus.CONFLICT),
    VOUCHER_OUT_OF_QUOTA("VOUCHER_429", HttpStatus.TOO_MANY_REQUESTS),
    INVALID_VOUCHER_PERIOD("VOUCHER_400", HttpStatus.BAD_REQUEST),
    VOUCHER_INVALID("VOUCHER_400", HttpStatus.BAD_REQUEST),
    VOUCHER_CODE_ALREADY_EXISTS("VOUCHER_409", HttpStatus.CONFLICT),


    UNAUTHORIZED("AUTH_401", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final HttpStatus status;

    ErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }
}

}
