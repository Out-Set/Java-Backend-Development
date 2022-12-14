package com.example.springmongoDB.demomongoDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {
    
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/getBooks")
    public List<Book> getBook(){
        return bookRepository.findAll();
    }

    @PostMapping("/insertBook")
    public void insertBook(@RequestBody CreateRequest request){

        Book book = new Book(request.getName(), request.getAuthorName(), request.getCost(), request.get_count());
        bookRepository.save(book);
    }

    @GetMapping("/getBookByAuthor")
    // in post-man; localhost:8080/getBookByAuthor?name=ABC
    public List<Book> getBookByAuthorName (@RequestParam(value = "name") String name){

        return bookRepository.findByAuthorName(name);
    }

    // or
    // @GetMapping("/getBookByAuthor/{name}")
    // // now in post-man; localhost:8080/getBookByAuthor/ABC
    // public List<Book> getBookByAuthorName (@PathVariable(value = "name") String name){

    //     return bookRepository.findByAuthorName(name);
    // }
}
