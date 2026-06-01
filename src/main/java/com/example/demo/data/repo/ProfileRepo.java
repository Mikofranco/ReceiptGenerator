package com.example.demo.data.repo;

import com.example.demo.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, String> {
}
