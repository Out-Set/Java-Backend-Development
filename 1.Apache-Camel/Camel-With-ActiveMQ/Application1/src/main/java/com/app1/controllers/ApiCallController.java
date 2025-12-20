package com.app1.controllers;

import com.app1.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiCallController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/fetch-api-data")
    public Object fetchApiData() {
        Object ob = apiService.fetchApiDataWithRetry(apiService);
        System.out.println(ob);
        return apiService.fetchApiDataWithRetry(apiService);
    }
}

