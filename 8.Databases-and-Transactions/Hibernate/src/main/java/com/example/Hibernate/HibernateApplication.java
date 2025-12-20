package com.example.Hibernate;

import com.example.Hibernate.Entity.Address;
import com.example.Hibernate.Entity.Student;
import com.example.Hibernate.Repository.AddressRepository;
import com.example.Hibernate.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class HibernateApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		// Reading Image

//		FileInputStream fis = new FileInputStream("src/main/java/com/example/Hibernate/download.png");
//		byte[] imageData = new byte[fis.available()];
//		fis.read(imageData);
//		addressRepository.save(new Address("12/34", "Noida", true, 234.4565, new Date(), imageData));


		addressRepository.save(new Address("12/34", "Noida", true, 234.4565, new Date()));

		Student student = studentRepository.findById(9).get();
		System.out.println(student.getId() + " " + student.getName() + " " + student.getAge());
	}
}
