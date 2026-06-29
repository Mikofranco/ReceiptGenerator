package com.example.demo.controller;

import com.example.demo.data.model.Customer;
import com.example.demo.data.model.Receipt;
import com.example.demo.dto.response.ReceiptResponse;
import com.example.demo.service.receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final ReceiptService receiptService;

    @GetMapping("/receipt/{userId}")
    public ResponseEntity<List<ReceiptResponse>> getReceiptByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(receiptService.getReceiptsByUserId(userId));
    }
}
