package com.example.demo.dto.response;

import com.example.demo.data.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileResponse {
    private String userId;
    private String username;
    private String message;
    private Profile profile;
}
