package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BusinessInfoResponse {
    private String id;
    private String email;
    private String logoUrl;
    private String phone;
    private String address;
    private String primaryColor;
    private String secondaryColor;
    private String websiteUrl;
}
