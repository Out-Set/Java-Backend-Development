package com.cachingWith.redis.controller;

import com.cachingWith.redis.entity.Users;
import com.cachingWith.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create User
    @PostMapping
    public Users createUser(@RequestBody Users users){
        return userService.createUser(users);
    }

    // Get by Id
    @GetMapping("/{userId}")
    public Object getUser(@PathVariable("userId") Integer userId){
        return userService.getUser(userId);
    }

    // Get All
    @GetMapping
    public Map<Object, Object> getAll(){
        return userService.getAll();
    }

    // Delete
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId){
        return userService.deleteUser(userId);
    }

    // Update
    @PutMapping
    public Object update(@RequestBody Users users){
        return userService.update(users);
    }

}
