package com.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomer {

    private String username;
    private String firstname;
    private String lastname;
    private String productname;
    private String productcategory;
    private double price;
    private String date;
}