package com.spring.security.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    private Integer rid;
    private String roleName;
    private List<String> authorities;
}