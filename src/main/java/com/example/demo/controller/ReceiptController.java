package com.example.demo.controller;

import com.example.demo.data.model.Receipt;
import com.example.demo.dto.requests.GenerateReceiptRequest;
import com.example.demo.dto.requests.UpdateReceiptRequest;
import com.example.demo.dto.response.GenerateReceiptResponse;
import com.example.demo.dto.response.PaginatedResponse;
import com.example.demo.dto.response.ReceiptDeletedResponse;
import com.example.demo.service.receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/receipt")
@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping("/generate")
    public ResponseEntity<Receipt> createReceipt(@RequestBody Receipt receipt) {
        Receipt response =receiptService.generateReceipt(receipt);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/generateById")
    public ResponseEntity<GenerateReceiptResponse> createReceiptByUserId(@RequestBody GenerateReceiptRequest request) {
        GenerateReceiptResponse response =receiptService.generateReceiptByUserId(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Receipt Controller is working! ✅");
    }
    @GetMapping("/")
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        return ResponseEntity.ok(receiptService.getAllReceipts());
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Receipt> getReceipt(@PathVariable String id) {
//        var receipt = receiptService.findById(id);
//        return ResponseEntity.ok(receipt);
//    }
    @PostMapping("/customer")
    public ResponseEntity<List<Receipt>> findByCustomerName(@RequestBody String customerName) {
        var receipt =receiptService.findByCustomerName(customerName);
        return ResponseEntity.ok(receipt);
    }
    @DeleteMapping("/{id}")

    public ResponseEntity<ReceiptDeletedResponse> deleteReceipt(@PathVariable String id) {
        return ResponseEntity.ok(receiptService.deleteReceipt(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Receipt> updateReceipt(@RequestBody UpdateReceiptRequest request,@PathVariable String id) {
        return ResponseEntity.ok(receiptService.updateReceipt(request,id));
    }
    @GetMapping
    public ResponseEntity<PaginatedResponse<Receipt>> getAllReceiptsByPage(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(receiptService.getPaginatedReceipts(page, size));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Receipt>> getReceiptByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(receiptService.getReceiptsByUserId(userId));
    }
}
