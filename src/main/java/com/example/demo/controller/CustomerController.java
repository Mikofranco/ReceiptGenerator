package com.example.demo.controller;

import com.example.demo.data.model.Customer;
import com.example.demo.dto.response.DeleteCustomerResponse;
import com.example.demo.dto.response.PaginatedResponse;
import com.example.demo.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customer));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customer, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCustomerResponse> deleteCustomer(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.deleteCustomer(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }
    @GetMapping
    public ResponseEntity<PaginatedResponse<Customer>> getPaginatedCustomers(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.paginatedFindAll(page, size));
    }
}
