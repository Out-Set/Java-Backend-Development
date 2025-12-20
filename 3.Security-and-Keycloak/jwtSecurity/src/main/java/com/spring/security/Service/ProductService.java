package com.spring.security.Service;

import com.spring.security.Entity.Product;
import com.spring.security.Repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getById(int id){
        return productRepository.findById(id).get();
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @PostConstruct
    public void insertProducts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Phone", 25000));
        products.add(new Product(2, "Laptop", 48000));
        products.add(new Product(3, "Bottle", 250));
        products.add(new Product(4, "Coffee", 150));
        products.add(new Product(5, "Bag", 500));
        products.add(new Product(6, "Notes", 550));

        productRepository.saveAll(products);
    }
}
