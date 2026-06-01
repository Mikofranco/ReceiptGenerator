package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RefreshTokenResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
}
