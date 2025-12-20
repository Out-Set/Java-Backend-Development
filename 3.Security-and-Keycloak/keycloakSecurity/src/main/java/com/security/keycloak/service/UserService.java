package com.security.keycloak.service;

import java.util.List;

import com.security.keycloak.entity.User;
import com.security.keycloak.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

	}

	public String createManyUser(List<User> user) {
		for (User usr : user) {
			usr.setPassword(passwordEncoder.encode(usr.getPassword()));
		}
		userRepo.saveAll(user);
		return "Users are registered successfully!";
	}

	public User readUser(int id) {
		return userRepo.findById(id).get();
	}

	public String updateUser(User user) {
		User existingUser = userRepo.findById(user.getId()).get();
		existingUser.setUsername(user.getUsername());
//        existingUser.setUserid(user.getUserid());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        existingUser.setRole(user.getRole());
		existingUser.setClient(user.getClient());
		userRepo.save(existingUser);
		return "User details updated successfully!";
	}

	public String deleteUser(int uid) {
		userRepo.deleteById(uid);
		return "User deleted successfully!";
	}
}
