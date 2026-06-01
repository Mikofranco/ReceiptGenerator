package com.example.demo.service.customer;

import com.example.demo.data.model.Customer;
import com.example.demo.dto.response.DeleteCustomerResponse;
import com.example.demo.dto.response.PaginatedResponse;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer, String id);
    DeleteCustomerResponse deleteCustomer(String id);
    Customer findById(String id);
    List<Customer> findByName(String name);
    List<Customer> findAll();
    PaginatedResponse<Customer> paginatedFindAll(int page, int size);
}
