package com.example.demo.controller;

import com.example.demo.data.model.Profile;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfileById(id));
    }
    @PatchMapping("")
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody Profile profile, String id){
        return ResponseEntity.status(HttpStatus.OK).body(profileService.updateProfile(profile, id));
    }

}
