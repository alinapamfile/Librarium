package com.app.librarium.controller;

import com.app.librarium.model.Book;
import com.app.librarium.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @PostMapping("books")
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @GetMapping("books/isbn/{ISBN}")
    public ResponseEntity<Object> getBookByISBN(@PathVariable String ISBN) {
        return bookService.getBookByISBN(ISBN);
    }

    @GetMapping("books/title/{title}")
    public ResponseEntity<Object> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("books/author/{author}")
    public ResponseEntity<Object> getBookByAuthor(@PathVariable String author) {
        return bookService.getBookByAuthor(author);
    }

    @PatchMapping("books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable String id, @RequestBody Map<String, Object> changes) {
        return bookService.updateBook(id, changes);
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        return bookService.deleteBook(id);
    }
}
