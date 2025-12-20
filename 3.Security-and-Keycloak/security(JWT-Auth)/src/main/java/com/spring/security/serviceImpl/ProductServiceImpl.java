package com.spring.security.serviceImpl;

import com.spring.security.service.IProductService;
import com.spring.security.entity.Product;
import com.spring.security.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getById(int id){
        return productRepository.findById(id).get();
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @Override
    public String update(Product product) {
        Product existingProduct = productRepository.findById(product.getPid()).orElse(null);
        if(existingProduct != null){
            existingProduct.setProductName(product.getProductName());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(product);
            return "Product Updated Successfully!";
        }
        return "Product with id: "+product.getPid()+", doesn't exist!";
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public String delete(int id) {
        productRepository.deleteById(id);
        return "Product Deleted Successfully!";
    }
}
