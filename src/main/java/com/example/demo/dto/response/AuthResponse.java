package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String userId;
    private String userName;
    private String firstName;
    private String lastName;
}
