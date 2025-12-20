package com.savan.config.cors.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/config/cors/spring")
public class SpringController {

    @GetMapping
    public String get(){
        return "Get api from spring !!";
    }

    @PostMapping
    public String post(@RequestBody Map<String, Object> reqBody){
        return "Post api from camel with request: "+reqBody;
    }
}
