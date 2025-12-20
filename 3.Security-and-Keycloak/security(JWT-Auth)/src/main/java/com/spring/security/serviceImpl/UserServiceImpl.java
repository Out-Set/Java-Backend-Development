package com.spring.security.serviceImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.spring.security.service.IUserService;
import com.spring.security.customExceptions.UserNotFoundException;
import com.spring.security.dto.UserDto;
import com.spring.security.entity.Authority;
import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public String saveUser(User user) {
		// Saving the user object
		String pwd = encoder.encode(user.getPassword());
		user.setPassword(pwd);
		userRepository.save(user);
		return "User Created Successfully!";
	}

	@Override
	public String updateUser(User user) {
		User existingUser = userRepository.findById(user.getUid()).orElseThrow(()->new UserNotFoundException("User with id " + user.getUid() + " doesn't exists"));
		user.setPassword(existingUser.getPassword());
		userRepository.save(user);
		return "User Updated Successfully!";
	}

	@Override
	public User findByUsername(String username) {
		//while login check whether the user is found in database or not
		Optional<User> opt = userRepository.findByUsername(username);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

	// For controller purpose
	@Override
	public UserDto getUserByUsername(String username) {
		Optional<User> opt = userRepository.findByUsername(username);
		if(opt.isPresent()){
			User user = opt.get();
			// Create DTO and set basic fields
			UserDto userDto = new UserDto();
			userDto.setUid(user.getUid());
			userDto.setName(user.getName());
			userDto.setUsername(user.getUsername());

			// Convert roles and authorities using streams
			List<Map<String, List<String>>> rolesAndAuthorities = user.getRoles().stream()
					.map(role -> Map.of(
							role.getRoleName(),
							role.getAuthorities().stream()
									.map(Authority::getAuthorityName)
									.collect(Collectors.toList())
					))
					.collect(Collectors.toList());

			userDto.setRolesAndAuthorities(rolesAndAuthorities);
			return userDto;
		}
		throw new UsernameNotFoundException("Username " + username + " not found");
	}

	@Override
	public List<UserDto> findAll() {
		return userRepository.findAll().stream().map(user -> {
			// Create DTO and set basic fields
			UserDto userDto = new UserDto();
			userDto.setUid(user.getUid());
			userDto.setName(user.getName());
			userDto.setUsername(user.getUsername());

			// Convert roles and authorities using streams
			List<Map<String, List<String>>> rolesAndAuthorities = user.getRoles().stream()
					.map(role -> Map.of(
							role.getRoleName(),
							role.getAuthorities().stream()
									.map(Authority::getAuthorityName)
									.collect(Collectors.toList())
					))
					.collect(Collectors.toList());

			userDto.setRolesAndAuthorities(rolesAndAuthorities);
			return userDto;
		}).collect(Collectors.toList());
	}

	@Override
	public String delete(String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

		// Unlink user from roles
		for (Role role : user.getRoles()) {
			role.getUsers().remove(user);
		}

		// Clear the user's role list
		user.getRoles().clear();

		// Now delete the user
		userRepository.delete(user);

		return "User Deleted Successfully!";
	}

	@Override
	public String updateUserPassword(String username, String password) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

		String pwd = encoder.encode(password);
		user.setPassword(pwd);
		userRepository.save(user);
		return "User Password Updated Successfully!";
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = findByUsername(username);
		if(null == user) throw new UsernameNotFoundException(username + " doesn't exist");

		// Map roles to authorities by fetching authorities of each role
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.flatMap(role -> role.getAuthorities().stream()
						.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName())))
				.distinct()
				.collect(Collectors.toList());

		System.out.println("Authorities in UserServiceImpl : "+authorities);

		// Return a new User object with username, encoded password, and authorities
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				authorities
		);

//		List<GrantedAuthority> list =
//				user.getRoles().stream()
//					.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//					.collect(Collectors.toList());
//
//		return new org.springframework.security.core.userdetails.User(
//				username, user.getPassword(), list);
	}

}