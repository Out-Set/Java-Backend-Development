package com.savan.keycloak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {

    // Get Access Token
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        System.out.println("inside login");
//        ResponseEntity<String> response = restTemplateService.getAccessToken(loginRequest.get("username"),
//                loginRequest.get("password"));
        return null;
    }

    // Verify Access Token

}
