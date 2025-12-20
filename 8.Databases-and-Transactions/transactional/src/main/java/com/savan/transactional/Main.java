package com.savan.transactional;

import com.savan.transactional.config.ProductConfig;
import com.savan.transactional.service.ProductService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ProductConfig.class);
        context.registerShutdownHook();
        ProductService productService = context.getBean("productService", ProductService.class);
        productService.savaProductInfo();
        context.close();
    }
}