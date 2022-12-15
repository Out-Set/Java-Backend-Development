package com.example.project.librarymanagement.Exception;


public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int id) {

        super("Book id not found : " + id);
    }
}