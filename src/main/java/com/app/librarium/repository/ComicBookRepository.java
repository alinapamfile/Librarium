package com.app.librarium.repository;

import com.app.librarium.model.ComicBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicBookRepository extends MongoRepository<ComicBook, String> {
    @Query(value = "{'series': {$regex : ?0, $options: 'i'}}")
    List<ComicBook> findBySeriesRegex(String regex);
}
