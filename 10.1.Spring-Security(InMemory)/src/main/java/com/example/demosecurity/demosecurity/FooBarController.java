package com.example.demosecurity.demosecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FooBarController {


    // my_role1 -> /foo*

    // my_role2 -> /bar*, /foo*
    
    // This requires authentication and authorization as well
    @GetMapping("/student")
    public String getFoo(){
        return "hello student";
    }

    // This requires authentication and authorization as well
    @GetMapping("/admin")
    public String getBar(){
        return "hello admin";
    }

    // This api doesn't require authentication and authorization
    // Open for general public
    @GetMapping("/visitor")
    public String getVisitor(){
        return "hello visitor";
    }

}
