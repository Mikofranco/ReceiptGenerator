package com.example.demo.service.receipt;

import com.example.demo.data.model.Receipt;
import com.example.demo.data.model.User;
import com.example.demo.data.repo.ReceiptRepo;
import com.example.demo.dto.requests.GenerateReceiptRequest;
import com.example.demo.dto.requests.UpdateReceiptRequest;
import com.example.demo.dto.response.GenerateReceiptResponse;
import com.example.demo.dto.response.PaginatedResponse;
import com.example.demo.dto.response.ReceiptDeletedResponse;
import com.example.demo.dto.response.ReceiptResponse;
import com.example.demo.service.Auth.UserService;
import com.example.demo.utils.Functions;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptImpl implements ReceiptService {
    private final ReceiptRepo receiptRepo;
    private final UserService userService;
    @Override
    public ReceiptResponse generateReceipt(Receipt receipt) {
        Receipt generatedReceipt = new Receipt();
        generatedReceipt.setDescription(receipt.getDescription());
        generatedReceipt.setCustomerName(receipt.getCustomerName());
        generatedReceipt.setReceiptFormats(receipt.getReceiptFormats());
        generatedReceipt.setDiscount(receipt.getDiscount());
        generatedReceipt.setCreatedAt(LocalDateTime.now());
        generatedReceipt.setTotalAmount(Functions
                .validateTotalAmount(receipt.getTotalAmount(), receipt.getDiscount()));
        var savedReceipt = receiptRepo.save(generatedReceipt);

        return mapReceiptResponse(savedReceipt);
    }

    @Nonnull
    private ReceiptResponse mapReceiptResponse(Receipt savedReceipt) {
        ReceiptResponse response = new ReceiptResponse();
        response.setId(savedReceipt.getId());
        response.setCreatedAt(savedReceipt.getCreatedAt());
        response.setDescription(savedReceipt.getDescription());
        response.setCustomerName(savedReceipt.getCustomerName());
        response.setReceiptFormats(savedReceipt.getReceiptFormats());
        response.setTotalAmount(savedReceipt.getTotalAmount());
        response.setDiscount(savedReceipt.getDiscount());
        response.setTotalAmount(savedReceipt.getTotalAmount());

        if (savedReceipt.getCreatedBy() != null) {
            response.setCreatedBy(savedReceipt.getCreatedBy().getId()); // or getName()
        }
        return response;
    }

    @Override
    public ReceiptResponse findById(String id) {
        Receipt foundReceipt= receiptRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Receipt not Found"));

        return mapReceiptResponse(foundReceipt);
    }

    @Override
    public List<Receipt> findByCustomerName(String customerName) {
        return receiptRepo.findByCustomerName(customerName)
                .orElseThrow(()-> new RuntimeException("Receipt not Found"));
    }

    @Override
    public ReceiptDeletedResponse deleteReceipt(String id) {
        if(receiptRepo.existsById(id)){
            receiptRepo.deleteById(id);
            ReceiptDeletedResponse response = new ReceiptDeletedResponse();
            response.setId(id);
            response.setMessage("Receipt Deleted");
            return response;
        }
        throw new RuntimeException("Receipt Not Found");
    }

    @Override
    public List<Receipt> getAllReceipts() {
        return receiptRepo.findAll();
    }

    @Override
    public ReceiptResponse updateReceipt(UpdateReceiptRequest request, String id) {
       Receipt initialReceipt =  receiptRepo.findById(id).orElseThrow(()-> new RuntimeException("Receipt Not Found"));
       if(request.getDescription() != null && !request.getDescription().isEmpty()){
           initialReceipt.setDescription(request.getDescription());
       }
       if(request.getCustomerName() != null && !request.getCustomerName().isEmpty()){
           initialReceipt.setCustomerName(request.getCustomerName());
       }
       if(request.getDiscount() > 0 || request.getDiscount() < 0){
           initialReceipt.setDiscount(request.getDiscount());
       }
       if(request.getTotalAmount().doubleValue() > 0){
           initialReceipt.setTotalAmount(request.getTotalAmount());
       }
       initialReceipt.setUpdatedAt(LocalDateTime.now());
       Receipt readyToMapReceipt = receiptRepo.save(initialReceipt);
       return mapReceiptResponse(readyToMapReceipt);
    }

    @Override
    public PaginatedResponse<Receipt> getPaginatedReceipts(int page, int pageSize) {
        int totalCount = getAllReceipts().size();
        Pageable pageable = PageRequest.of(page, pageSize);
        var retrievedReceipts =receiptRepo.findAll(pageable).getContent();
        PaginatedResponse<Receipt> response = new PaginatedResponse<>();
        response.setData(retrievedReceipts);
        response.setCurrentPage(page);
        response.setTotalCount(totalCount);
        response.setTotalPage(totalCount / pageSize);
        return response;
    }

    @Override
    public List<ReceiptResponse> getReceiptsByUserId(String userId) {

        return receiptRepo.findByCreatedBy_Id(userId)
                .stream()
                .map(this::mapReceiptResponse)
                .toList();
    }

    @Override
    public GenerateReceiptResponse generateReceiptByUserId(GenerateReceiptRequest request) {
       User foundUser =  userService.findUserById(request.getUserId());

        Receipt generatedReceipt = new Receipt();
        generatedReceipt.setCreatedBy(foundUser);
        generatedReceipt.setDescription(request.getDescription());
        generatedReceipt.setCustomerName(request.getCustomerName());
        generatedReceipt.setReceiptFormats(request.getReceiptFormats());
        generatedReceipt.setDiscount(request.getDiscount());
        generatedReceipt.setCreatedAt(LocalDateTime.now());
        generatedReceipt.setTotalAmount(Functions
                .validateTotalAmount(request.getTotalAmount(), request.getDiscount()));
        var savedReceipt =  receiptRepo.save(generatedReceipt);

        GenerateReceiptResponse response = new GenerateReceiptResponse();
        response.setId(savedReceipt.getId());
        response.setCreatedAt(savedReceipt.getCreatedAt());
        response.setDescription(savedReceipt.getDescription());
        response.setCustomerName(savedReceipt.getCustomerName());
        response.setTotalAmount(savedReceipt.getTotalAmount());
        response.setUpdatedAt(savedReceipt.getUpdatedAt());
        response.setCreatedBy(savedReceipt.getCreatedBy().getId());
        return response;
    }

}
