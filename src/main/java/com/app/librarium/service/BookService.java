package com.app.librarium.service;

import com.app.librarium.model.Book;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BookService {
    ResponseEntity<Object> addBook(Book book);
    ResponseEntity<Object> getBookById(String id);
    ResponseEntity<Object> getBookByISBN(String ISBN);
    ResponseEntity<Object> getBookByTitle(String title);
    ResponseEntity<Object> getBookByAuthor(String author);
    ResponseEntity<Object> updateBook(String id, Map<String, Object> changes);
    ResponseEntity<Object> deleteBook(String id);
}
