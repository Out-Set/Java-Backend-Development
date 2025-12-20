package com.spring.security.controller;

import com.spring.security.service.IProductService;
import com.spring.security.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @GetMapping("/read")
    // @PreAuthorize("hasAuthority('GET')")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("/read/{id}")
    // @PreAuthorize("hasAuthority('GET')")
    public Product getAll(@PathVariable int id){
        return productService.getById(id);
    }

    @PostMapping("/create")
    // @PreAuthorize("hasAuthority('POST')")
    public Product getAll(@RequestBody Product product){
        return productService.create(product);
    }

    @PutMapping("/update")
    // @PreAuthorize("hasAuthority('PUT')")
    public String update(@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping("/delete/{id}")
    // @PreAuthorize("hasAuthority('DELETE')")
    public String update(@PathVariable int id){
        return productService.delete(id);
    }
}
