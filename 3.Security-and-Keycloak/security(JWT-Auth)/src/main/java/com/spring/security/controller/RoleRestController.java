package com.spring.security.controller;

import com.spring.security.service.IRoleService;
import com.spring.security.dto.RoleDto;
import com.spring.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private IRoleService roleService;

    // Create
    @PostMapping
    public String createRole(@RequestBody Role role){
        return roleService.save(role);
    }

    // Read All
    @GetMapping
    public List<RoleDto> readAll(){
        return roleService.readAll();
    }

    // Read All
    @GetMapping("{roleName}")
    public RoleDto getByRoleName(@PathVariable String roleName){
        return roleService.getByRoleName(roleName);
    }

    // Delete
    @DeleteMapping("/{roleName}")
    public String delete(@PathVariable String roleName){
        return roleService.delete(roleName);
    }
}
