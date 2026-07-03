package com.example.demo.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
}
