package com.exampledemojpa.demojpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exampledemojpa.demojpa.Models.Book;
import com.exampledemojpa.demojpa.Repository.BookRepository;

@SpringBootApplication
public class DemoJpaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);

	}

	@Autowired
	BookRepository bookRepository;

 
	@Override
	public void run(String... args) throws Exception {

		Book b1 = new Book();
		b1.setId(1);
		b1.setCost(30);
		b1.setName("ABC");
		b1.setAuthorName("Rahul");

		bookRepository.save(b1);
	}

}
