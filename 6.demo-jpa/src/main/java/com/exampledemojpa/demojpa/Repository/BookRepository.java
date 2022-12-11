package com.exampledemojpa.demojpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exampledemojpa.demojpa.Models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
