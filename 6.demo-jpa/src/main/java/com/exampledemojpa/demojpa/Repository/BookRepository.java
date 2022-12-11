package com.exampledemojpa.demojpa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exampledemojpa.demojpa.Models.Book; 

public interface BookRepository extends JpaRepository<Book, Integer> {
    
    // List<Book> findByAuthorName(String name);
    // List<Book> findByName(String name);

    // Note: It does't matter whether you write your prooerty_name starting with small or capital
	// i.e. cost or Cost, Id or id, Name or name, Authorname or authorname
    List<Book> findByCost(int cost);
    List<Book> findBycost(int cost);

    Book findById(int id);
    Book findByid(int id);

    // Why only single book, not a list of Book?
    // Because, for every unique id it will return a single result.
    // i.e. A single book not a list of book

    // So, you can find out anything by using: findBy + property_name
    // But property_name must be exist in the table.
    // i.e. Book table(id, authorName, cost, name)


    // Finding by: findBy + other-name
    // 1. MySQL native query
    @Query(value = "select * from book b where b.author_name=:my_name", nativeQuery = true)
    public List<Book> findByAuthor(String my_name);

    // 2. JPQL(java persistance query language)
    @Query(value = "select b from Book b where b.authorName=:authors_name")
    public List<Book> findByAuthors(String authors_name);

}
