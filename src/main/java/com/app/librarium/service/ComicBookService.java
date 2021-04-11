package com.app.librarium.service;

import com.app.librarium.model.ComicBook;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ComicBookService {
    ResponseEntity<Object> addComicBook(ComicBook comicBook);
    ResponseEntity<Object> getComicBookById(String id);
    ResponseEntity<Object> getComicBookBySeries(String series);
    ResponseEntity<Object> updateComicBook(String id, Map<String, Object> changes);
    ResponseEntity<Object> deleteComicBook(String id);
}
