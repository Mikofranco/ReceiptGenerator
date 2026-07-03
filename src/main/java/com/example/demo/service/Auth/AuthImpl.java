package com.example.demo.service.Auth;

import com.example.demo.data.model.BusinessInfo;
import com.example.demo.data.model.Profile;
import com.example.demo.data.model.User;
import com.example.demo.data.repo.ProfileRepo;
import com.example.demo.data.repo.UserRepo;
import com.example.demo.dto.requests.AuthRequest;
import com.example.demo.dto.response.*;
import com.example.demo.service.MailService;
import com.example.demo.service.jwt.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.service.businessInfo.BusinessInfoImpl.mapBusinessInfoResponse;

@Service
@RequiredArgsConstructor
public class AuthImpl implements UserService {
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepo  profileRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MailService mailService;

    @Override @Transactional
    public ProfileResponse register(User userRequest) {
        // 1. Check if username already exists
        if (userRepo.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userRequest.getUsername());
        }

        // 2. Create and save User first
        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        newUser.setFirstname(userRequest.getFirstname());
        newUser.setLastname(userRequest.getLastname());

        User savedUser = userRepo.save(newUser);

        // 3. Create Profile using the SAVED user (with generated ID)
        Profile savedProfile = createProfile(savedUser);

        return mapProfileResponse(savedUser, savedProfile);
    }

    @Nonnull
    public static ProfileResponse mapProfileResponse(User savedUser, Profile savedProfile) {
        ProfileResponse response = new ProfileResponse();
        response.setProfile(mapProfileToUserdata(savedProfile));
        response.setUsername(savedUser.getUsername());
        response.setUserId(savedUser.getId());
        response.setMessage("Register successfully");
        response.setUserResponse(mapUserResponse(savedUser));
        return response;
    }

    @Nonnull
    public static UserProfile mapProfileToUserdata(Profile savedProfile) {

        return new UserProfile(savedProfile.getId(),  savedProfile.getFirstName(),
                savedProfile.getLastName(),savedProfile.getEmail(), savedProfile.getAddress(),
                savedProfile.getCity(), savedProfile.getState(), savedProfile.getCountry(), savedProfile.getProfileType(), savedProfile.getAvatarUrl());
    }


    @Nonnull
    public static ProfileResponse mapProfileResponse(User savedUser, Profile savedProfile, BusinessInfo savedBusinessInfo) {
        ProfileResponse response = new ProfileResponse();
        response.setProfile(mapProfileToUserdata(savedProfile));
        response.setUsername(savedUser.getUsername());
        response.setUserId(savedUser.getId());
        response.setMessage("Register successfully");
        response.setUserResponse(mapUserResponse(savedUser));
        response.setBusinessInfoResponse(mapBusinessInfoResponse(savedBusinessInfo));
        return response;
    }

    @Nonnull
    private Profile createProfile(User savedUser) {     // Note: renamed parameter for clarity

        Profile newProfile = new Profile();

        newProfile.setUser(savedUser);
        newProfile.setFirstName(savedUser.getFirstname());// Important: Link to already saved User
        newProfile.setLastName(savedUser.getLastname());
        newProfile.setEmail(savedUser.getUsername());   // or savedUser.getEmail() if you have it

        // Do NOT manually set Profile ID unless it's a natural key
        // newProfile.setId(...)  ← Remove this line

        return profileRepo.save(newProfile);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user =userRepo.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        if(user.getPassword().equals(bCryptPasswordEncoder.encode(user.getPassword()))) {
//            userDetailsService.loadUserByUsername(username);
//        }
//        throw new RuntimeException("Invalid password");
//    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        System.out.println(request.getUsername() + " " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

        String token = jwtService.generateToken(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUserId(user.getId());
        authResponse.setFirstName(user.getFirstname());
        authResponse.setLastName(user.getLastname());
        authResponse.setUserName(user.getUsername());
        return authResponse;
    }

    @Override
    public RefreshTokenResponse refresh(String token) {
        return null;
    }

    @Override
    public UserResponse findById(String id) {
        User foundUser= userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
        return mapUserResponse(foundUser);
    }

    @Override
    public User findUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
    }

    @Nonnull
    public static UserResponse mapUserResponse(User foundUser) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(foundUser.getId());
        userResponse.setUsername(foundUser.getUsername());
        userResponse.setFirstname(foundUser.getFirstname());
        userResponse.setLastname(foundUser.getLastname());
        return userResponse;
    }

}
