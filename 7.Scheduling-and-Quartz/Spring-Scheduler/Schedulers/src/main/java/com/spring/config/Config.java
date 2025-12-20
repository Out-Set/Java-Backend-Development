package com.spring.config;

import lombok.Getter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Config {

//    @Value("${spring.datasource.url}")
    private String url;
}
