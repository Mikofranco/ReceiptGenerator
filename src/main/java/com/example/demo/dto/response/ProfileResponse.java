package com.example.demo.dto.response;

import com.example.demo.data.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileResponse {
    private String userId;
    private String username;
    private String message;
    private UserProfile profile;
    private UserResponse userResponse;
    private BusinessInfoResponse businessInfoResponse;
}

