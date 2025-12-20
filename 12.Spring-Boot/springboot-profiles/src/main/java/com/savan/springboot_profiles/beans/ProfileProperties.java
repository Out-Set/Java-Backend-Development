package com.savan.springboot_profiles.beans;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.profile")
public class ProfileProperties {

    private String name;
    private String environment;
}
