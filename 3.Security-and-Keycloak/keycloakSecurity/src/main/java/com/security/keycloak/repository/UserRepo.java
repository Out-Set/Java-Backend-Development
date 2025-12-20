package com.security.keycloak.repository;

import com.security.keycloak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query(value = "select * from users where username=:userName", nativeQuery = true)
	User findByUserName(String userName);

}
