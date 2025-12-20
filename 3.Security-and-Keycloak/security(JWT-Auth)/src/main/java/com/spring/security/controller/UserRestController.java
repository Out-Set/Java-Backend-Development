package com.spring.security.controller;

import java.util.List;

import com.spring.security.service.IUserService;
import com.spring.security.dto.UserDto;
import com.spring.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService userService;

	// Create
	@PostMapping
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		String resp = userService.saveUser(user);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Read by username
	@GetMapping("/{username}")
	public UserDto findByUsername(@PathVariable String username){
		return userService.getUserByUsername(username);
	}

	// Read all
	@GetMapping
	public List<UserDto> findAll(){
		return userService.findAll();
	}

	// Update user
	@PutMapping
	public String updateUser(@RequestBody User user){
		return userService.updateUser(user);
	}

	// Update password
	@PutMapping("/{username}")
	public String updateUserPassword(@PathVariable String username, @RequestParam String password){
		return userService.updateUserPassword(username, password);
	}

	// Delete User
	@DeleteMapping("/{username}")
	public String delete(@PathVariable String username){
		return userService.delete(username);
	}

}