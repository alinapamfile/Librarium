package com.app.librarium.service;

import com.app.librarium.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity<Object> addUser(User user);
    ResponseEntity<Object> getUserById(String id);
    ResponseEntity<Object> getUserByEmail(String email);
    ResponseEntity<Object> updateUser(String id, Map<String, Object> changes);
    ResponseEntity<Object> deleteUser(String id);
}
