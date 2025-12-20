package com.spring.security.controller;

import com.spring.security.model.Customer;
import com.spring.security.model.RegisterCustomer;
import com.spring.security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CustomerController {
    private final CustomerService service;
    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody RegisterCustomer registerCustomer){
        return ResponseEntity.ok(service.addCustomer(registerCustomer));
    }
    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        List<Customer> customers = service.getAllCustomer();
        return ResponseEntity.ok(customers);
    }
    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id){
        Customer customer = service.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    @PutMapping("/updateCustomerById/{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable("id") long id, @RequestBody RegisterCustomer registerCustomer){
        return ResponseEntity.ok(service.updateCustomerById(id, registerCustomer));
    }
    @DeleteMapping("/deleteCustomerById/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") long id){
        return ResponseEntity.ok(service.deleteCustomerById(id));
    }
}