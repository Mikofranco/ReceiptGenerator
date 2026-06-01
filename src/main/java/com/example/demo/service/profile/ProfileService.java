package com.example.demo.service.profile;

import com.example.demo.data.model.Profile;
import com.example.demo.dto.response.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ProfileService {
    ProfileResponse updateProfile(Profile profile, String id);
    ProfileResponse getProfileById(String id);
    ProfileResponse getProfileByUsername(String username);
    ProfileResponse getProfileByEmail(String email);
    ProfileResponse uploadProfileImage(MultipartFile file);
    ProfileResponse uploadProfileImage(File file);
}
