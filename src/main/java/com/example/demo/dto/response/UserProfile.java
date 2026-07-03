package com.example.demo.dto.response;

import com.example.demo.data.model.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile{
    private String profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
    private ProfileType UserType;
    private String avatarUrl;
}
