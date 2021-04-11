package com.app.librarium.service;

import com.app.librarium.model.Book;
import com.app.librarium.model.ComicBook;
import com.app.librarium.model.Item;
import com.app.librarium.model.Rental;
import com.app.librarium.repository.BookRepository;
import com.app.librarium.repository.ComicBookRepository;
import com.app.librarium.repository.RentalRepository;
import com.app.librarium.repository.UserRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ComicBookRepository comicBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> addRental(Rental rental) {
        Item item;

        if (bookRepository.findById(rental.getItemId()).orElse(null) == null) {
            item = comicBookRepository.findById(rental.getItemId()).orElse(null);
        } else {
            item = bookRepository.findById(rental.getItemId()).orElse(null);
        }

        if (item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", "false",
                    "message", "Item doesn't exist"
            ));
        }

        if (userRepository.findById(rental.getUserId()).orElse(null) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        if (item.getQuantity() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", "false",
                    "message", "Item is not available"
            ));
        }

        try {
            rental = rentalRepository.insert(rental);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "rental", rental
        ));
    }

    @Override
    public ResponseEntity<Object> getRentalById(String id) {
        Rental rental = rentalRepository.findById(id).orElse(null);

        if (rental == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Rental doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "rental", rental
        ));
    }

    @Override
    public ResponseEntity<Object> getRentalByItemId(String itemId) {
        List<Rental> rentals = rentalRepository.findByItemId(itemId);

        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Rental doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "rentals", rentals
        ));
    }

    @Override
    public ResponseEntity<Object> getRentalByUserId(String userId) {
        List<Rental> rentals = rentalRepository.findByUserId(userId);

        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Rental doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "rentals", rentals
        ));
    }

    @Override
    public ResponseEntity<Object> updateRental(String id, Map<String, Object> changes) {
        Rental rental = rentalRepository.findById(id).orElse(null);

        if (rental == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Rental doesn't exist"
            ));
        }

        Item item = null;
        for (String key : changes.keySet()) {
            if (key.equals("returnedDate")) {
                rental.setRentedDate((LocalDate) changes.get("rentedDate"));

                if (bookRepository.findById(rental.getItemId()).orElse(null) == null) {
                    item = comicBookRepository.findById(rental.getItemId()).orElse(null);
                } else {
                    item = bookRepository.findById(rental.getItemId()).orElse(null);
                }
                assert item != null;
                item.setQuantity(item.getQuantity() + 1);
            }
        }

        try {
            rentalRepository.save(rental);
            if (item != null) {
                if (item instanceof Book) {
                    bookRepository.save((Book) item);
                } else {
                    comicBookRepository.save((ComicBook) item);
                }
            }
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "rental", rental
        ));
    }

    @Override
    public ResponseEntity<Object> deleteRental(String id) {
        Rental rental = rentalRepository.findById(id).orElse(null);

        if (rental == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "Rental doesn't exist"
            ));
        }

        try {
            rentalRepository.deleteById(id);
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
