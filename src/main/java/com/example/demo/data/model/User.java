package com.example.demo.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
