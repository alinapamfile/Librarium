package com.app.librarium.repository;

import com.app.librarium.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByISBN(String ISBN);
    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    List<Book> findByTitleRegex(String regex);
    @Query(value = "{'author': {$regex : ?0, $options: 'i'}}")
    List<Book> findByAuthorRegex(String regex);
}
