package com.spring.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "products_tab")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String productName;
    private int price;
}
