package com.example.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Autowired
	UserServiceRepository userServiceRepository;
	@Override
	public void run(String... args) throws Exception {

		userServiceRepository.save(new MyUserDetails("sawan", "sawan", true, "admin:student"));
		userServiceRepository.save(new MyUserDetails("anuj", "anuj", true, "student"));
	}
}
