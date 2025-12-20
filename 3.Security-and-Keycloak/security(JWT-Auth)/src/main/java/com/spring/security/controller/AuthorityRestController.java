package com.spring.security.controller;

import com.spring.security.service.IAuthorityService;
import com.spring.security.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityRestController {

    @Autowired
    private IAuthorityService authorityService;

    // Create
    @PostMapping
    public String createAuthority(@RequestBody Authority authority){
        return authorityService.save(authority);
    }

    // Read All
    @GetMapping
    public List<Authority> readAll(){
        return authorityService.readAll();
    }

    // Delete
    @DeleteMapping("/{authorityName}")
    public String delete(@PathVariable String authorityName){
        return authorityService.delete(authorityName);
    }
}
