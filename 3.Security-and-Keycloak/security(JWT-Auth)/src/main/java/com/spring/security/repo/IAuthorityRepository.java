package com.spring.security.repo;

import com.spring.security.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findByAuthorityName(String authority);

    void deleteByAuthorityName(String authority);
}
