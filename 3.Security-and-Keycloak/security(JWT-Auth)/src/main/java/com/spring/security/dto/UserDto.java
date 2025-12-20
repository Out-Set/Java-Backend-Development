package com.spring.security.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDto {

    private Integer uid;
    private String name;
    private String username;
    private List<Map<String, List<String>>> rolesAndAuthorities;
}
