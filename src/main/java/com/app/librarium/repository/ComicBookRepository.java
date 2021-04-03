package com.app.librarium.repository;

import com.app.librarium.model.ComicBook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComicBookRepository extends MongoRepository<ComicBook, String> {
}
