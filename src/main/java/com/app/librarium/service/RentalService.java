package com.app.librarium.service;

import com.app.librarium.model.Rental;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RentalService {
    ResponseEntity<Object> addRental(Rental rental);
    ResponseEntity<Object> getRentalById(String id);
    ResponseEntity<Object> getRentalByItemId(String itemId);
    ResponseEntity<Object> getRentalByUserId(String itemId);
    ResponseEntity<Object> updateRental(String id, Map<String, Object> changes);
    ResponseEntity<Object> deleteRental(String id);
}
