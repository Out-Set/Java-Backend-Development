package com.example.project.librarymanagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.example.project.librarymanagement.DataAccessLayer.User;
import com.example.project.librarymanagement.DataAccessLayer.UserRepository;
import com.example.project.librarymanagement.Exception.UserNotFoundException;
import com.example.project.librarymanagement.Util.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class UserResource {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private UserRepository repository;
    // Find
    @GetMapping("/users")
    List<User> findAll() {
        return repository.findAll();
    }

    @PostMapping("/users")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) throws Exception {
    // @ , ., com, Email Validation
        if(UserValidator.isValidUser(newUser))
        return repository.save(newUser);
        else throw new Exception();
    }

    // Find a given user
    @GetMapping("/users/{id}")
    User findOne(@PathVariable int id) {
        // LOGGER.info("/users/{id} called with id "+ id);
        // Optional<User> user = repository.findById(id);
        // return user.get();

        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

}

