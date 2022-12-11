package com.exampledemojpa.demojpa;

import java.util.ArrayList;
import java.util.List;

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

		// 1. Inserting values through parameterized constructor
		// Book b1 = new Book(3, "XYZ", "Ajay", 60);
		// bookRepository.save(b1);

		// 2. You can also do it as shown below.
		// b1.setId(1);
		// b1.setCost(50);
		// b1.setName("ABC");
		// b1.setAuthorName("Mehul");
		// bookRepository.save(b1);


		// 3. For inserting multiple values
		// List <Book> books = new ArrayList<>();

		// Book b1 = new Book(1, "ABC", "Rahul", 40);
		// Book b2 = new Book(2, "XYZ", "Ajay", 60);

		// books.add(b1);
		// books.add(b2);
		// bookRepository.saveAll(books);

		// For inserting single values: save() method
		// For inserting single values: saveAll() method
		


		// 4. Reading/Get data from table
		// Use findAll() method, it return list of object.

		// System.out.println(bookRepository.findAll());
		// System.out.println(bookRepository.findByAuthorName("Rahul"));
		// System.out.println(bookRepository.findByName("ABC"));
		// System.out.println(bookRepository.findById(1));

		// Note: It does't matter whether you write your prooerty_name starting with small or capital letter.
		// i.e. cost or Cost, Id or id, Name or name, Authorname or authorname
		// System.out.println(bookRepository.findByCost(40));
		// System.out.println(bookRepository.findBycost(40));

		// System.out.println(bookRepository.findById(1));
		// System.out.println(bookRepository.findByid(1));

  
		// 5.findBy + other-name
		// System.out.println(bookRepository.findByAuthor("Rahul"));
		// System.out.println(bookRepository.findByAuthors("Ajay"));



		// 6. For Auto generation of unique Ids, no need to pass id.

		// In 'create' mode(application.properties)
		// List <Book> books = new ArrayList<>();

		// Book b1 = new Book("ABC", "Rahul", 40);
		// Book b2 = new Book("XYZ", "Ajay", 60);

		// books.add(b1);
		// books.add(b2);
		// bookRepository.saveAll(books);

		// In 'update' mode(application.properties)
		// Book b1 = new Book("Q21", "Jaya", 70);
		// bookRepository.save(b1);


		// 7. update data in table
		
		// 8. delete data from table
	}

}
