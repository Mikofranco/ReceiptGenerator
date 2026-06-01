package com.example.demo.dto.response;

import com.example.demo.data.model.Profile;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginUserResponse {
    private String token;
    private Profile profile;

}
