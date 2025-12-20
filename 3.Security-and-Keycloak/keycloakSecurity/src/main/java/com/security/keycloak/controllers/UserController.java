package com.security.keycloak.controllers;

import com.security.keycloak.entity.User;
import com.security.keycloak.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createManyUser")
    public String createManyUser(@RequestBody List<User> user) {
        return userService.createManyUser(user);
    }

    @GetMapping("/getUser/{id}")
    public User readUser(@PathVariable int id) {
        return userService.readUser(id);
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}
