package com.spring.security.service;

import com.spring.security.model.Customer;
import com.spring.security.model.RegisterCustomer;
import java.util.List;

public interface CustomerService {

    String addCustomer(RegisterCustomer customer);
    Customer getCustomerById(long id);
    List<Customer> getAllCustomer();
    String updateCustomerById(long id, RegisterCustomer registerCustomer);
    String deleteCustomerById(long id);
}