package com.example.demo.data.repo;

import com.example.demo.data.model.BusinessInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessInfoRepo extends JpaRepository<BusinessInfo, String> {
}
