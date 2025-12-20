package com.spring.security.service;

import com.spring.security.dto.UserDto;
import com.spring.security.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {

	// Save into database(during signin)
	String saveUser(User user);

	// Update user into database(during signin)
	String updateUser(User user);

	// when logging with username and password check whether user if found or not
	User findByUsername(String username);

	UserDto getUserByUsername(String username);

	List<UserDto> findAll();

	@Transactional
	String delete(String username);

	String updateUserPassword(String username, String password);
}