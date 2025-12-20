package com.spring.security.service;

import com.spring.security.entity.Product;

import java.util.List;

public interface IProductService {

    // find by id
    Product getById(int id);

    // find all
    List<Product> getAll();

    String update(Product product);

    Product create(Product product);

    String delete(int id);
}
