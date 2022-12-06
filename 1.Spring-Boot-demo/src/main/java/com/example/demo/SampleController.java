package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SampleController {
    
    @GetMapping("/hi")
    public String sayHi(){
        return "Hello from our server";
    }

    @GetMapping("/search")
    public String searchAWord(@RequestParam String q){
        DataStore db = new DataStore();
        return db.getFromDB(q);
    }
}
