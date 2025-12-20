package com.spring.security.serviceImpl;

import com.spring.security.service.IAuthorityService;
import com.spring.security.customExceptions.AuthorityNotFoundException;
import com.spring.security.entity.Authority;
import com.spring.security.entity.Role;
import com.spring.security.repo.IAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    private IAuthorityRepository authorityRepository;

    @Override
    public String save(Authority authority) {
        authorityRepository.save(authority);
        return "Authority Created Successfully!";
    }

    @Override
    public List<Authority> readAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority findByAuthorityName(String authority) {
        return authorityRepository.findByAuthorityName(authority);
    }

    @Override
    public String delete(String authorityName) {
        Authority authority = authorityRepository.findByAuthorityName(authorityName);
        if (null == authority) throw  new AuthorityNotFoundException("Authority " + authorityName + " not found");

        // Unlink from roles
        for (Role role : authority.getRoles()) {
            role.getAuthorities().remove(authority);
        }

        // Clear the list to break bidirectional relationship
        authority.getRoles().clear();

        // Delete the authority
        authorityRepository.delete(authority);

        return "Authority Deleted Successfully!";
    }
}
