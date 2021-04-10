package com.app.librarium.controller;

import com.app.librarium.model.ComicBook;
import com.app.librarium.service.ComicBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ComicBookController {

    @Autowired
    private ComicBookServiceImpl comicBookService;

    @PostMapping("comicBooks")
    public ResponseEntity<Object> addComicBook(@RequestBody ComicBook comicBook) {
        return comicBookService.addComicBook(comicBook);
    }

    @GetMapping("comicBooks/{id}")
    public ResponseEntity<Object> getComicBookById(@PathVariable String id) {
        return comicBookService.getComicBookById(id);
    }

    @GetMapping("comicBooks/series/{series}")
    public ResponseEntity<Object> getComicBookBySeries(@PathVariable String series) {
        return comicBookService.getComicBookBySeries(series);
    }

    @PatchMapping("comicBooks/{id}")
    public ResponseEntity<Object> updateComicBook(@PathVariable String id, @RequestBody Map<String, Object> changes) {
        return comicBookService.updateComicBook(id, changes);
    }

    @DeleteMapping("comicBooks/{id}")
    public ResponseEntity<Object> deleteComicBook(@PathVariable String id) {
        return comicBookService.deleteComicBook(id);
    }
}
