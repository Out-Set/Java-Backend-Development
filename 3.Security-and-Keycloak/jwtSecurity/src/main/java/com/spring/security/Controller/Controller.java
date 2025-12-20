package com.spring.security.Controller;

import com.spring.security.Entity.Product;
import com.spring.security.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class Controller {

    @Autowired
    private ProductService productService;

    @GetMapping("/welcome")
    public String message(){
        return "Welcome here !!";
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getAll(@PathVariable int id){
        return productService.getById(id);
    }
}
