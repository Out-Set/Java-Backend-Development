package com.security.keycloak.repository;

import com.security.keycloak.entity.Apis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApisRepo extends JpaRepository<Apis, Integer> {

}
