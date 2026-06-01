package com.example.demo.service.profile;

import com.example.demo.data.model.Profile;
import com.example.demo.data.repo.ProfileRepo;
import com.example.demo.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Service
@RequiredArgsConstructor
public class ProfileImpl implements ProfileService {
    private final ProfileRepo profileRepo;

    @Override
    public ProfileResponse updateProfile(Profile profile, String id) {
       Profile foundProfile = profileRepo.findById(id)
               .orElseThrow(()-> new RuntimeException("Profile not found"));

       if(!profile.getCity().isEmpty()){
           foundProfile.setCity(profile.getCity());
       }if(!profile.getCountry().isEmpty()){
           foundProfile.setCountry(profile.getCountry());
       }if(!profile.getAddress().isEmpty()){
           foundProfile.setAddress(profile.getAddress());
       }if(!profile.getEmail().isEmpty()){
           foundProfile.setEmail(profile.getEmail());
       }if(!profile.getState().isEmpty()){
           foundProfile.setState(profile.getState());
       }if(!profile.getFirstName().isEmpty()){
           foundProfile.setFirstName(profile.getFirstName());
       }if(!profile.getLastName().isEmpty()){
           foundProfile.setLastName(profile.getLastName());
       }
       Profile savedProfile = profileRepo.save(foundProfile);
       if(savedProfile.getId()!=null){
           throw new RuntimeException("Profile not updated");
       }
        return new ProfileResponse( savedProfile.getUser().getId(),
                savedProfile.getUser().getUsername(), "Profile Updated", savedProfile);
    }

    @Override
    public ProfileResponse getProfileById(String id) {
        Profile foundProfile =profileRepo.findById(id).orElseThrow(()-> new RuntimeException("Profile not found"));
        System.out.println("==============================" + foundProfile.toString());
        return new ProfileResponse( foundProfile.getUser().getId(),
                foundProfile.getUser().getUsername(), "Profile Updated", foundProfile);
    }

    @Override
    public ProfileResponse getProfileByUsername(String username) {
        return null;
    }

    @Override
    public ProfileResponse getProfileByEmail(String email) {
        return null;
    }

    @Override
    public ProfileResponse uploadProfileImage(MultipartFile file) {
        return null;
    }

    @Override
    public ProfileResponse uploadProfileImage(File file) {
        return null;
    }
}
