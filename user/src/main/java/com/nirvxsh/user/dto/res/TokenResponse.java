package com.nirvxsh.user.dto.res;

import lombok.Data;

@Data
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
}
