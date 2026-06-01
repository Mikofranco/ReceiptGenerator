package com.example.demo.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String customerName;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private float discount;
    @Enumerated(EnumType.STRING)
    private ReceiptFormats receiptFormats;
}
