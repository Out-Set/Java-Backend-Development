package com.savan.transactional.repo;

import com.savan.transactional.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public void saveProduct(Product product){
        String sql = "INSERT INTO PRODUCT(name) VALUES (?)";
        Object[] args = {product.getName()};
        jdbcTemplate.update(sql, args);
    }

}
