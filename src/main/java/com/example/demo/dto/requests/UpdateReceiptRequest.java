package com.example.demo.dto.requests;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateReceiptRequest {
    private String description;
    private String customerName;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @CreatedDate
    private LocalDateTime updatedAt;
    private float discount;

}
