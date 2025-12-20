package com.app2.controller;

import com.app2.entity.UserDtl;
import com.app2.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userDtl")
public class Controller {

    @Autowired
    private Service service;

    @PostMapping("")
    public UserDtl save(@RequestBody UserDtl userDtl){
        return service.save(userDtl);
    }

    @GetMapping("")
    public List<UserDtl> get(){
        return service.getAll();
    }

}
