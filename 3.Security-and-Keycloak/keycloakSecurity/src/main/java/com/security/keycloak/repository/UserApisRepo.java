package com.security.keycloak.repository;

import java.util.List;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.entity.User;
import com.security.keycloak.entity.UserApis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApisRepo extends JpaRepository<UserApis, Integer> {

    @Query(value = "select * from user_apis where api_id=:api_id", nativeQuery = true)
    UserApis findByApiId(@Param("api_id") int api_id);

    UserApis findByApiAndUser(Apis apiId, User user);
    List<UserApis> findByUser(User user);
}
