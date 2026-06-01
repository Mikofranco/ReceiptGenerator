package com.example.demo.service.Auth;

import com.example.demo.data.model.Profile;
import com.example.demo.data.model.User;
import com.example.demo.data.repo.ProfileRepo;
import com.example.demo.data.repo.UserRepo;
import com.example.demo.dto.requests.AuthRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.ProfileResponse;
import com.example.demo.dto.response.RefreshTokenResponse;
import com.example.demo.service.jwt.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthImpl implements UserService {
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepo  profileRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

        User savedUser = userRepo.save(newUser);

        // 3. Create Profile using the SAVED user (with generated ID)
        Profile savedProfile = createProfile(savedUser);

        return new ProfileResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                "Registered Successfully",
                savedProfile
        );
    }

    @Nonnull
    private Profile createProfile(User savedUser) {     // Note: renamed parameter for clarity

        Profile newProfile = new Profile();

        newProfile.setUser(savedUser);           // Important: Link to already saved User
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
        authResponse.setUserName(user.getUsername());
        return authResponse;
    }

    @Override
    public RefreshTokenResponse refresh(String token) {
        return null;
    }

}
