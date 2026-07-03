package com.example.demo.service.profile;

import com.example.demo.data.model.Profile;
import com.example.demo.data.model.User;
import com.example.demo.data.repo.ProfileRepo;
import com.example.demo.dto.response.BusinessInfoResponse;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.Auth.UserService;
import com.example.demo.service.businessInfo.BusinessInfoService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.example.demo.service.Auth.AuthImpl.mapProfileResponse;
import static com.example.demo.service.Auth.AuthImpl.mapUserResponse;

@Service
@RequiredArgsConstructor
public class ProfileImpl implements ProfileService {
    private final ProfileRepo profileRepo;
    private final BusinessInfoService businessInfoService;

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
       BusinessInfoResponse businessInfoResponse = businessInfoService.
               findBusinessInfoById(savedProfile.getUser().getId());


        return mapProfileResponse(savedProfile.getUser(), foundProfile);

    }

    @Override
    public ProfileResponse getProfileById(String id) {
        Profile foundProfile =profileRepo.findByUserId(id)
                .orElseThrow(()-> new RuntimeException("Profile not found"));
        return mapProfileResponse(foundProfile.getUser(), foundProfile);
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
