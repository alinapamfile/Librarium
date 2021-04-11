package com.app.librarium.repository;

import com.app.librarium.model.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends MongoRepository<Rental, String> {
    List<Rental> findByItemId(String itemId);
    List<Rental> findByUserId(String userId);
}
