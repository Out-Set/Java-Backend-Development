package com.savan.keycloak.controller;

import com.savan.keycloak.dto.KeycloakUser;
import com.savan.keycloak.service.KeycloakUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @PostMapping("/create")
    public String createUser(@RequestBody KeycloakUser keycloakUser) {
        System.out.println("*********************** Creating New User ***********************");
        return keycloakUserService.createUser(keycloakUser);
    }

    @GetMapping("/assign/role")
    public String assignRole(@RequestParam String userId, @RequestParam String roles){
        return keycloakUserService.assignRole(userId, roles);
    }
}