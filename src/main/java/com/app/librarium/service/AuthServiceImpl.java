package com.app.librarium.service;

import com.app.librarium.model.User;
import com.app.librarium.repository.UserRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<Object> register(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", "false",
                    "message", "User already exists"
            ));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        try {
            userRepository.insert(user);
        } catch (MongoException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", "false"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true"
        ));
    }

    @Override
    public ResponseEntity<Object> login(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", "false",
                    "message", "User doesn't exist"
            ));
        }

        if (!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "success", "false",
                    "message", "Incorrect password"
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "success", "true"
        ));
    }
}