package com.example.batchProcessing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RController {

    @GetMapping("/welcome")
    public String getWelcomeMessage(){
        return "Welcome to batch processing through scheduling.";
    }
}
