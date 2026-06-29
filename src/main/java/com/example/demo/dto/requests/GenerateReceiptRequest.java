package com.example.demo.dto.requests;

import com.example.demo.data.model.ReceiptFormats;
import com.example.demo.data.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter @Getter
public class GenerateReceiptRequest {
    private String userId;
    private String description;
    private String customerName;
    private BigDecimal totalAmount;
    private float discount;
    private ReceiptFormats receiptFormats;

}
