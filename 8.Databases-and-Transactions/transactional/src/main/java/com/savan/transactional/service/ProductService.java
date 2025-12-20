package com.savan.transactional.service;

import com.savan.transactional.dto.Product;
import com.savan.transactional.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Transactional // connection start
    public void savaProductInfo(){
        Product product = new Product();
        for (int i=1; i<=10; i++){
            product.setName("Test Product "+i);
            productRepo.saveProduct(product);
            if (i==7) throw new RuntimeException("An error occurred!!");
        }
    }
    // commit
    // close

}
