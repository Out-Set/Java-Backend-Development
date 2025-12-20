package com.spring.security.service;

import com.spring.security.entity.Authority;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAuthorityService {

    String save(Authority authority);

    List<Authority> readAll();

    Authority findByAuthorityName(String authority);

    @Transactional
    String delete(String authorityName);
}
