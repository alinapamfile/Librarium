package com.app.librarium.service;

import com.app.librarium.model.Book;
import com.app.librarium.model.User;
import com.app.librarium.repository.UserRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> addUser(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());

        if (foundUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", "false",
                    "message", "User already exists"
            ));
        }

        try {
            user = userRepository.insert(user);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "user", user
        ));
    }

    @Override
    public ResponseEntity<Object> getUserById(String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "user", user
        ));
    }

    @Override
    public ResponseEntity<Object> getUserByEmail(String email) {
        List<User> users = userRepository.findByEmailLike(email);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "users", users
        ));
    }

    @Override
    public ResponseEntity<Object> updateUser(String id, Map<String, Object> changes) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        changes.keySet().forEach(key -> {
            if (key.equals("admin")) {
                user.setAdmin((Boolean) changes.get(key));
            }
        });

        try {
            userRepository.save(user);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true",
                "user", user
        ));
    }

    @Override
    public ResponseEntity<Object> deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        try {
            userRepository.deleteById(id);
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
