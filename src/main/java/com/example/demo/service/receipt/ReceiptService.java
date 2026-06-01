package com.example.demo.service.receipt;

import com.example.demo.data.model.Receipt;
import com.example.demo.dto.requests.UpdateReceiptRequest;
import com.example.demo.dto.response.PaginatedResponse;
import com.example.demo.dto.response.ReceiptDeletedResponse;

import java.util.List;

public interface ReceiptService {
    Receipt generateReceipt(Receipt receipt);
    Receipt findById(String id);
    List<Receipt> findByCustomerName(String customerName);
    ReceiptDeletedResponse deleteReceipt(String id);
    List<Receipt> getAllReceipts();
    Receipt updateReceipt(UpdateReceiptRequest request, String id);
    PaginatedResponse<Receipt> getPaginatedReceipts(int page, int pageSize);

}
