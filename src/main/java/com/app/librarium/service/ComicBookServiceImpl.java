package com.app.librarium.service;

import com.app.librarium.model.Book;
import com.app.librarium.model.ComicBook;
import com.app.librarium.repository.BookRepository;
import com.app.librarium.repository.ComicBookRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComicBookServiceImpl implements ComicBookService {

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Override
    public ResponseEntity<Object> addComicBook(ComicBook comicBook) {
        try {
            comicBook = comicBookRepository.insert(comicBook);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "comicBook", comicBook
        ));
    }

    @Override
    public ResponseEntity<Object> getComicBookById(String id) {
        ComicBook comicBook = comicBookRepository.findById(id).orElse(null);

        if (comicBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Comic book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "comicBook", comicBook
        ));
    }

    @Override
    public ResponseEntity<Object> getComicBookBySeries(String series) {
        List<ComicBook> comicBooks = comicBookRepository.findBySeriesRegex(series);

        if (comicBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Comic book doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "comicBooks", comicBooks
        ));
    }

    @Override
    public ResponseEntity<Object> updateComicBook(String id, Map<String, Object> changes) {
        ComicBook comicBook = comicBookRepository.findById(id).orElse(null);

        if (comicBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Comic book doesn't exist"
            ));
        }

        changes.keySet().forEach(key -> {
            switch (key) {
                case "series": { comicBook.setSeries((String) changes.get("series")); break; }
                case "number": { comicBook.setNumber((Integer) changes.get("number")); break; }
                case "year": { comicBook.setYear((Integer) changes.get("year")); break; }
                case "rating": { comicBook.setRating((Double) changes.get("rating")); break; }
            }
        });

        try {
            comicBookRepository.save(comicBook);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "comicBook", comicBook
        ));
    }

    @Override
    public ResponseEntity<Object> deleteComicBook(String id) {
        ComicBook comicBook = comicBookRepository.findById(id).orElse(null);

        if (comicBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Comic book doesn't exist"
            ));
        }

        try {
            comicBookRepository.deleteById(id);
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
