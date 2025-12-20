package com.spring.security.serviceImpl;

import com.spring.security.service.IRoleService;
import com.spring.security.customExceptions.RoleNotFoundException;
import com.spring.security.dto.RoleDto;
import com.spring.security.entity.Authority;
import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.repo.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public String save(Role role) {
        roleRepository.save(role);
        return "Role Created Successfully!";
    }

    @Override
    public List<RoleDto> readAll() {
        return roleRepository.findAll().stream()
                .map(role -> {
                    RoleDto roleDto = new RoleDto();
                    roleDto.setRid(role.getRid());
                    roleDto.setRoleName(role.getRoleName());

                    List<String> authorities = role.getAuthorities().stream()
                            .map(Authority::getAuthorityName)
                            .collect(Collectors.toList());

                    roleDto.setAuthorities(authorities);
                    return roleDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    // For controller purpose
    @Override
    public RoleDto getByRoleName(String role) {
        Role existingRole = roleRepository.findByRoleName(role);
        if(null == existingRole) throw new RoleNotFoundException("Role " + role + " doesn't exists");
        RoleDto roleDto = new RoleDto();
        roleDto.setRid(existingRole.getRid());
        roleDto.setRoleName(existingRole.getRoleName());

        List<String> authorities = existingRole.getAuthorities().stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.toList());

        roleDto.setAuthorities(authorities);
        return roleDto;
    }

    @Override
    public String delete(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if(null == role) throw new RoleNotFoundException("Role " + roleName + " not found");

        // Unlink from users
        for (User user : role.getUsers()) {
            user.getRoles().remove(role);
        }

        // Unlink from authorities
        for (Authority authority : role.getAuthorities()) {
            authority.getRoles().remove(role);
        }

        // Clear both associations from role to avoid orphan persistence issues
        role.getUsers().clear();
        role.getAuthorities().clear();

        // Now delete the role
        roleRepository.delete(role);

        return "Role Deleted Successfully!";
    }
}
