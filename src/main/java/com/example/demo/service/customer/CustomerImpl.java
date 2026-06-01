package com.example.demo.service.customer;

import com.example.demo.data.model.Customer;
import com.example.demo.data.repo.CustomerRepo;
import com.example.demo.dto.response.DeleteCustomerResponse;
import com.example.demo.dto.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = new Customer();
        createdCustomer.setAddress(customer.getAddress());
        createdCustomer.setEmail(customer.getEmail());
        createdCustomer.setFirstName(customer.getFirstName());
        createdCustomer.setLastName(customer.getLastName());
        createdCustomer.setCity(customer.getCity());
        createdCustomer.setState(customer.getState());
        createdCustomer.setCountry(customer.getCountry());
         return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer, String id) {
        Customer foundCustomer = findById(id);
        if(customer.getAddress() != null) {
            foundCustomer.setAddress(customer.getAddress());
        }
        if(customer.getEmail() != null) {
            foundCustomer.setEmail(customer.getEmail());
        }
        if(customer.getFirstName() != null) {
            foundCustomer.setFirstName(customer.getFirstName());
        }
        if(customer.getLastName() != null) {
            foundCustomer.setLastName(customer.getLastName());
        }
        if(customer.getCity() != null) {
            foundCustomer.setCity(customer.getCity());
        }
        if(customer.getState() != null) {
            foundCustomer.setState(customer.getState());
        }
        if(customer.getCountry() != null) {
            foundCustomer.setCountry(customer.getCountry());
        }
        return customerRepo.save(foundCustomer);
    }

    @Override
    public DeleteCustomerResponse deleteCustomer(String id) {
       Customer customer = findById(id);
       customerRepo.delete(customer);
        return new DeleteCustomerResponse(id, "Delete Successful");
    }

    @Override
    public Customer findById(String id) {
        return customerRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Customer not found"));
    }

    @Override
    public List<Customer> findByName(String name) {
//        return customerRepo.findByCustomerName(name.toLowerCase()).orElseThrow();
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public PaginatedResponse<Customer> paginatedFindAll(int page, int size) {
        int totalCount = findAll().size();
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> customers = customerRepo.findAll(pageable).getContent();
        PaginatedResponse<Customer> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setCurrentPage(page);
        paginatedResponse.setTotalCount(totalCount);
        paginatedResponse.setTotalPage(totalCount/size);
        paginatedResponse.setData(customers);
        return paginatedResponse;
    }
}
