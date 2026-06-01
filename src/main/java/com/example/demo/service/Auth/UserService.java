package com.example.demo.service.Auth;

import com.example.demo.data.model.User;
import com.example.demo.dto.requests.AuthRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.ProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    ProfileResponse register(User user);
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<User> getUsers();
    AuthResponse login(AuthRequest request);

}
