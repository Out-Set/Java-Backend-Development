package com.pk.development;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pk")
public class DevelopmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevelopmentApplication.class, args);
	}

}
