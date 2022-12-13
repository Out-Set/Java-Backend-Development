package com.example.jpajointable.jpajointable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.jpajointable.jpajointable.Models.Book;
import com.example.jpajointable.jpajointable.Models.BookCategory;
import com.example.jpajointable.jpajointable.Repository.BookCategoryRepository;
import com.example.jpajointable.jpajointable.Repository.BookRepository;

@SpringBootApplication
public class JpaJoinTableApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(JpaJoinTableApplication.class, args);
	}

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookCategoryRepository bookCategoryRepository;

	@Override
    public void run(String...args) throws Exception {

		Set<Book> books = new HashSet();

		// Successfully inserted
		Book b1 = new Book("C++", "Bjorne", 90, 2);
		bookRepository.save(b1); 

		// Can not be inserted as bookCategory 4(foriegn key constraint) does not exist.
		Book b2 = new Book("Ruby", "Joy", 50, 4);
		bookRepository.save(b2); 

		// Book b2 = new Book("Python", "ATUL", 90);
		// books.add(b2); 


		// bookCategoryRepository.save(new BookCategory("Non Programming Language"));
		// bookCategoryRepository.save(new BookCategory("Programming Language", books));

	}
}
