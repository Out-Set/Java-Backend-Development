package com.spring.security.service;

import java.util.Map;

public interface IRoleAndAuthorityService {

    // Add roles to user
    String addRolesToUser(Map<String, Object> userWithRoles);

    // Delete roles from user
    String deleteRolesFromUser(Map<String, Object> userWithRoles);

    // Add authorities to role
    String addAuthoritiesToRole(Map<String, Object> roleWithAuthorities);

    // Delete authorities from role
    String deleteAuthoritiesFromRole(Map<String, Object> roleWithAuthorities);
}
