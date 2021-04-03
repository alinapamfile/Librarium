package com.app.librarium.service;

import com.app.librarium.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> register(User user);
    ResponseEntity<Object> login(User user);
}
