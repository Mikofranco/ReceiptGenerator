package com.example.demo.data.repo;

import com.example.demo.data.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepo extends JpaRepository<Receipt, String> {
    Optional<List<Receipt>> findByCustomerName(String customerName);
    List<Receipt> findByCreatedBy_Id(String userId);
}
