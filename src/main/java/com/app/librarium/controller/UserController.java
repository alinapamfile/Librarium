package com.app.librarium.controller;

import com.app.librarium.model.User;
import com.app.librarium.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("users/email/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody Map<String, Object> changes) {
        return userService.updateUser(id, changes);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
 }
