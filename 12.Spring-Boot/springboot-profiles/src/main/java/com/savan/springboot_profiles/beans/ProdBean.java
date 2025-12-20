package com.savan.springboot_profiles.beans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdBean {

    public ProdBean(){
        System.out.println("From Prod Bean ...");
    }
}
