package com.savan.springboot_profiles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TestService {

    // Since @Profile() works only at class level, so we can not use it at method level
    // This is how we can profile at method level with the help of 'Environment'
    // Either use @Profile() at class level or use as shown below

    @Autowired
    private Environment environment;

    @ConditionalOnProperty(name = "master.service.enabled", havingValue = "true")
    public String getMasterResponse(){
        if (Arrays.asList(environment.getActiveProfiles()).contains("master")) {
            System.out.println("From Master Profile ...");
            return "From Master Profile ...";
        } else {
            System.out.println("Master Profile is not Activated ...");
            return "Profile-Not-Active";
        }
    }

    @ConditionalOnProperty(name = "slave.service.enabled", havingValue = "true")
    public String getSlaveResponse(){
        if (Arrays.asList(environment.getActiveProfiles()).contains("slave")) {
            System.out.println("From Slave Profile ...");
            return "From Slave Profile ...";
        } else {
            System.out.println("Slave Profile is not Activated ...");
            return "Profile-Not-Active";
        }
    }
}
