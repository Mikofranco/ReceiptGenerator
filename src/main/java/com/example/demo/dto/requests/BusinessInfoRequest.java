package com.example.demo.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BusinessInfoRequest {
    private String userId;
    private String email;
    private String logoUrl;
    private String phone;
    private String address;
    private String primaryColor;
    private String secondaryColor;
    private String websiteUrl;
}
