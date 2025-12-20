package com.sawan.namedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NamedQueryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NamedQueryApplication.class, args);
	}

	@Autowired
	private Repo repository;

	@Override
	public void run(String... args) throws Exception {
		Entity e1 = new Entity(1, "hello");
		repository.save(e1);
		Entity e2 = new Entity(2, "Good");
		repository.save(e2);
	}
}
