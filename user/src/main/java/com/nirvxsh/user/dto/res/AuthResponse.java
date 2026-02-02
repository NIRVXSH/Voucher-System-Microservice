package com.nirvxsh.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
}
