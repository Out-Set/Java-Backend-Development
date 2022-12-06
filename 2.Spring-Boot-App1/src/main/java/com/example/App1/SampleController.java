package com.example.App1;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    
    DBUser db = new DBUser();

    // GET APIs
    // http://localhost:8080/users   --> Get
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return db.getAllUsers();
    }

    // http://localhost:8080/users/(id)   --> Get
    @GetMapping("/users/{id}")
    public User getAUser(@PathVariable int id) {
        return db.getAUser(id);
    }

    @GetMapping("/user")
    public User getAUser(@RequestParam String q) {
        return db.getAUserByName(q);
    } 


    // POST APIs 
    @PostMapping("/users")
    public boolean createUser(@RequestBody User user){
        return db.addUser(user);
    }

    @DeleteMapping("/users")
    public boolean deleteAUser(@PathVariable int id){
        return db.deleteAUser(id);
    }
}
