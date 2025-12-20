package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
        System.out.println("TimeZone: " + TimeZone.getDefault().getID());
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        System.out.println("TimeZone: " + TimeZone.getDefault().getID());
		SpringApplication.run(SecurityApplication.class, args);
	}
}
