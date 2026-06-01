package com.example.demo.data.repo;

import com.example.demo.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, String> {
    Optional<List<Customer>> findByFirstNameOrLastName(String firstName, String lastName);
}
