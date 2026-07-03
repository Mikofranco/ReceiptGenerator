package com.example.demo.service.receipt;

import com.example.demo.data.model.Receipt;
import com.example.demo.dto.requests.GenerateReceiptRequest;
import com.example.demo.dto.requests.UpdateReceiptRequest;
import com.example.demo.dto.response.GenerateReceiptResponse;
import com.example.demo.dto.response.PaginatedResponse;
import com.example.demo.dto.response.ReceiptDeletedResponse;
import com.example.demo.dto.response.ReceiptResponse;

import java.util.List;

public interface ReceiptService {
    ReceiptResponse generateReceipt(Receipt receipt);
    ReceiptResponse findById(String id);
    List<Receipt> findByCustomerName(String customerName);
    ReceiptDeletedResponse deleteReceipt(String id);
    List<Receipt> getAllReceipts();
    ReceiptResponse updateReceipt(UpdateReceiptRequest request, String id);
    PaginatedResponse<Receipt> getPaginatedReceipts(int page, int pageSize);
    List<ReceiptResponse> getReceiptsByUserId(String userId);
    GenerateReceiptResponse generateReceiptByUserId(GenerateReceiptRequest request);

}
