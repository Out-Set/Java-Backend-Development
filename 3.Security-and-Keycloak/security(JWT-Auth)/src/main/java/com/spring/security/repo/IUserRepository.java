package com.spring.security.repo;

import com.spring.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	void deleteByUsername(String username);
}