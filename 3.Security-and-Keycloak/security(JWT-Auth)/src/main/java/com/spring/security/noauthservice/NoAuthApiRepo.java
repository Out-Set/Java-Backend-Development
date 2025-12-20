package com.spring.security.noauthservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoAuthApiRepo extends JpaRepository<NoAuthApi, Integer> {

}
