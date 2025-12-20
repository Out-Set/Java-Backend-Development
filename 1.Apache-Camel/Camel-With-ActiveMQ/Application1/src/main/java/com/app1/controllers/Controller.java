package com.app1.controllers;

import com.app1.entity.UserFullDtl;
import com.app1.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userFullDtl")
public class Controller {

    @Autowired
    private Service service;

    @PostMapping("/single")
    public UserFullDtl save(@RequestBody UserFullDtl userFullDtl){
        return service.save(userFullDtl);
    }

    @PostMapping("/multi")
    public List<UserFullDtl> saveAll(@RequestBody List<UserFullDtl> userFullDtl){
        return service.saveAll(userFullDtl);
    }

    @GetMapping("")
    public List<UserFullDtl> get(){
        return service.getAll();
    }
}
