package com.example.springmongoDB.demomongoDB.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, Integer> {
    
}
