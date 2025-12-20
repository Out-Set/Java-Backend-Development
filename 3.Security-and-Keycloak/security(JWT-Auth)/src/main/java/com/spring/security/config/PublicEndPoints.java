package com.spring.security.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PublicEndPoints {

    private List<String> endpoints = new ArrayList<>();

    // @PostConstruct
    public void setPublicEndPoints(){
        endpoints.add("/rest/demo/route");
        log.info("Public End Points: {}", endpoints);
    }

    public List<String> getPublicEndPoints(){
        if(!endpoints.isEmpty()){
            return endpoints;
        }
        setPublicEndPoints();
        return endpoints;
    }
}
