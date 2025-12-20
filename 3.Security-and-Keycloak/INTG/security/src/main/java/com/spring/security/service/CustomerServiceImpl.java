package com.spring.security.service;

import com.spring.security.dao.CustomerRepository;
import com.spring.security.model.Customer;
import com.spring.security.model.RegisterCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;
    @Override
    public String addCustomer(RegisterCustomer customer) {
        var customerData = Customer.builder()
                .username(customer.getUsername())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .productname(customer.getProductname())
                .productcategory(customer.getProductcategory())
                .price(customer.getPrice())
                .date(customer.getDate())
                .build();
        repository.save(customerData);
        return "customer added successfully";
    }

    @Override
    public Customer getCustomerById(long id) {
        Optional<Customer> customer  = repository.findById(id);
        return customer.get();
    }

    @Override
    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }

    @Override
    public String updateCustomerById(long id, RegisterCustomer registerCustomer) {
        Optional<Customer> data = repository.findById(id);
        data.get().setId(id);
        data.get().setFirstname(registerCustomer.getFirstname());
        data.get().setLastname(registerCustomer.getLastname());
        data.get().setProductname(registerCustomer.getProductname());
        data.get().setProductcategory(registerCustomer.getProductcategory());
        data.get().setPrice(registerCustomer.getPrice());
        data.get().setDate(registerCustomer.getDate());
        repository.save(data.get());
        return "customer updated successfully";
    }

    @Override
    public String deleteCustomerById(long id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return "customer deleted successfully";
    }
}