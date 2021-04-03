package com.app.librarium.repository;

import com.app.librarium.model.ComicBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicBookRepository extends MongoRepository<ComicBook, String> {
}
