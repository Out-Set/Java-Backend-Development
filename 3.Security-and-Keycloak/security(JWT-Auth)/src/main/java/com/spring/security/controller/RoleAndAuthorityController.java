package com.spring.security.controller;

import com.spring.security.service.IRoleAndAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/role-authority")
public class RoleAndAuthorityController {

    @Autowired
    private IRoleAndAuthorityService roleAndAuthorityService;

    // Add roles to user
    @PostMapping("/user/roles/add")
    public String addRolesToUser(@RequestBody Map<String, Object> userWithRoles) {
        return roleAndAuthorityService.addRolesToUser(userWithRoles);
    }

    // Delete roles from user
    @PostMapping("/user/roles/delete")
    public String deleteRolesFromUser(@RequestBody Map<String, Object> userWithRoles) {
        return roleAndAuthorityService.deleteRolesFromUser(userWithRoles);
    }

    // Add authorities to role
    @PostMapping("/role/authorities/add")
    public String addAuthoritiesToRole(@RequestBody Map<String, Object> roleWithAuthorities) {
        return roleAndAuthorityService.addAuthoritiesToRole(roleWithAuthorities);
    }

    // Delete authorities from role
    @PostMapping("/role/authorities/delete")
    public String deleteAuthoritiesFromRole(@RequestBody Map<String, Object> roleWithAuthorities) {
        return roleAndAuthorityService.deleteAuthoritiesFromRole(roleWithAuthorities);
    }
}
