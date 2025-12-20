package com.spring.security.serviceImpl;

import com.spring.security.service.IAuthorityService;
import com.spring.security.service.IRoleAndAuthorityService;
import com.spring.security.service.IRoleService;
import com.spring.security.service.IUserService;
import com.spring.security.customExceptions.AuthorityNotFoundException;
import com.spring.security.customExceptions.RoleNotFoundException;
import com.spring.security.entity.Authority;
import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleAndAuthorityServiceImpl implements IRoleAndAuthorityService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public String addRolesToUser(Map<String, Object> userWithRoles) {
        User existingUser = userService.findByUsername(userWithRoles.get("username").toString());
        if (existingUser == null) throw new UsernameNotFoundException("Username " + userWithRoles.get("username").toString() + " doesn't exist");

        List<String> roles = (List<String>) userWithRoles.get("roles");

        Set<Role> currentRoles = new HashSet<>(existingUser.getRoles());

        for (String roleName : roles) {
            System.out.println("role: " + roleName);
            Role existingRole = roleService.findByRoleName(roleName);
            if (existingRole == null) throw new RoleNotFoundException("Role " + roleName + " doesn't exist");
            currentRoles.add(existingRole);
        }

        existingUser.setRoles(new ArrayList<>(currentRoles));
        userService.updateUser(existingUser);

        return "Roles added to user successfully!";
    }

    @Override
    public String deleteRolesFromUser(Map<String, Object> userWithRoles) {
        String username = userWithRoles.get("username").toString();
        List<String> roleNamesToRemove = (List<String>) userWithRoles.get("roles");

        User user = userService.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Username " + username + " doesn't exist");

        List<Role> currentRoles = user.getRoles();
        Iterator<Role> iterator = currentRoles.iterator();

        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (roleNamesToRemove.contains(role.getRoleName())) {
                role.getUsers().remove(user); // Remove from role side
                iterator.remove(); // Remove from user side
            }
        }

        user.setRoles(currentRoles);
        userService.updateUser(user);

        return "Roles removed from user successfully!";
    }


    @Override
    public String addAuthoritiesToRole(Map<String, Object> roleWithAuthorities) {
        Role existingRole = roleService.findByRoleName(roleWithAuthorities.get("roleName").toString());
        if (existingRole == null) throw new RoleNotFoundException("Role " + roleWithAuthorities.get("roleName").toString() + " doesn't exist");

        List<String> authorities = (List<String>) roleWithAuthorities.get("authorities");

        Set<Authority> currentAuthorities = new HashSet<>(existingRole.getAuthorities());

        for (String authorityName : authorities) {
            System.out.println("Authority: " + authorityName);
            Authority existingAuthority = authorityService.findByAuthorityName(authorityName);
            if (existingAuthority == null) throw new AuthorityNotFoundException("Authority " + authorityName + " doesn't exist");
            currentAuthorities.add(existingAuthority);
        }

        existingRole.setAuthorities(new ArrayList<>(currentAuthorities));
        roleService.save(existingRole);

        return "Authorities added to role successfully!";
    }

    @Override
    public String deleteAuthoritiesFromRole(Map<String, Object> roleWithAuthorities) {
        String roleName = roleWithAuthorities.get("roleName").toString();
        List<String> authorityNamesToRemove = (List<String>) roleWithAuthorities.get("authorities");

        Role role = roleService.findByRoleName(roleName);
        if (role == null)  throw new RoleNotFoundException("Role " + roleName + " doesn't exist");

        List<Authority> currentAuthorities = role.getAuthorities();
        Iterator<Authority> iterator = currentAuthorities.iterator();

        while (iterator.hasNext()) {
            Authority authority = iterator.next();
            if (authorityNamesToRemove.contains(authority.getAuthorityName())) {
                authority.getRoles().remove(role); // Remove role from authority side
                iterator.remove(); // Remove authority from role side
            }
        }

        role.setAuthorities(currentAuthorities);
        roleService.save(role);

        return "Authorities removed from role successfully!";
    }

}
