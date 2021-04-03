package com.app.librarium.repository;

import com.app.librarium.model.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends MongoRepository<Rental, String> {
}
