package com.savan.springboot_profiles.beans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("uat")
public class UatBean {

    public UatBean(){
        System.out.println("From Uat Bean ...");
    }
}
