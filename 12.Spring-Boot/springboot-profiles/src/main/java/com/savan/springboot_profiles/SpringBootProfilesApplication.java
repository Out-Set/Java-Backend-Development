package com.savan.springboot_profiles;

import com.savan.springboot_profiles.beans.ProfileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootProfilesApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootProfilesApplication.class, args);

		// Getting ProfileProperties
		ProfileProperties profileProperties = context.getBean(ProfileProperties.class);
		System.out.println("Name: "+profileProperties.getName());
		System.out.println("Environment: "+profileProperties.getEnvironment());
	}

}
