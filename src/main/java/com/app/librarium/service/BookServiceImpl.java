package com.app.librarium.service;

import com.app.librarium.model.Book;
import com.app.librarium.repository.BookRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<Object> addBook(Book book) {
        Book foundBook = bookRepository.findByISBN(book.getISBN());

        if (foundBook != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", "false",
                    "message", "Book already exists"
            ));
        }

        if (book.getQuantity().compareTo(0) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", "false",
                    "message", "Quantity can't be negative"
            ));
        }

        try {
            book = bookRepository.insert(book);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "book", book
        ));
    }

    @Override
    public ResponseEntity<Object> getBookById(String id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "book", book
        ));
    }

    @Override
    public ResponseEntity<Object> getBookByISBN(String ISBN) {
        Book book = bookRepository.findByISBN(ISBN);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "book", book
        ));
    }

    @Override
    public ResponseEntity<Object> getBookByTitle(String title) {
        List<Book> books = bookRepository.findByTitleRegex(title);

        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "books", books
        ));
    }

    @Override
    public ResponseEntity<Object> getBookByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorRegex(author);

        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "books", books
        ));
    }

    @Override
    public ResponseEntity<Object> updateBook(String id, @RequestBody Map<String, Object> changes) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        changes.keySet().forEach(key -> {
            switch (key) {
                case "title": { book.setTitle((String) changes.get("title")); break; }
                case "author": { book.setAuthor((String) changes.get("author")); break; }
                case "year": { book.setYear((Integer) changes.get("year")); break; }
                case "rating": { book.setRating((Double) changes.get("rating")); break; }
                case "quantity": {
                    Integer quantity = (Integer) changes.get("quantity");
                    if (quantity.compareTo(0) >= 0) {
                        book.setQuantity(quantity);
                    }
                    break;
                }
            }
        });

        try {
            bookRepository.save(book);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "book", book
        ));
    }

    @Override
    public ResponseEntity<Object> deleteBook(String id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Book doesn't exist"
            ));
        }

        try {
            bookRepository.deleteById(id);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true"
        ));
    }
}
