package com.example.jpabeans.demojpabeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @Autowired
    HelloWorld helloWorld;

    @GetMapping("/func")
    public void fun(){
        System.out.println(helloWorld);
        
    }
}
