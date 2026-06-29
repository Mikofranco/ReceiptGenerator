package com.example.demo.dto.response;

import com.example.demo.data.model.ReceiptFormats;
import com.example.demo.data.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter @Getter
public class GenerateReceiptResponse {
    private String id;
    private String description;
    private String customerName;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private float discount;
    private ReceiptFormats receiptFormats;
    private String createdBy;
    private String updatedBy;
}
