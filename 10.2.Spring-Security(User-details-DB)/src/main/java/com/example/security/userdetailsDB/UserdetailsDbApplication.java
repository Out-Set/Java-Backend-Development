package com.example.security.userdetailsDB;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserdetailsDbApplication implements CommandLineRunner {
	private final UserRepository userRepository;

	public UserdetailsDbApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserdetailsDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Password is encoded online, sawan: $2a$10$7mcMhakOrMpr6pVmH48PVuF2FxWbllimXFvQy27wJYV5v/javvAry
		// Password is encoded online, anuj: $2a$10$edNnMEcxG24rTh6yyCslt.eIjHtEdvneJCPosBQj2zMPdCnRucTpq
		User user = new User("sawan", "$2a$10$7mcMhakOrMpr6pVmH48PVuF2FxWbllimXFvQy27wJYV5v/javvAry", true, "admin:student");
		User user1 = new User("anuj", "$2a$10$edNnMEcxG24rTh6yyCslt.eIjHtEdvneJCPosBQj2zMPdCnRucTpq", true, "student");
		userRepository.save(user);
		userRepository.save(user1);
	}
}
