package com.spring.security.service;

import com.spring.security.dto.RoleDto;
import com.spring.security.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRoleService {

    String save(Role role);

    List<RoleDto> readAll();

    Role findByRoleName(String roleName);

    RoleDto getByRoleName(String roleName);

    @Transactional
    String delete(String roleName);
}
