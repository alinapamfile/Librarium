package com.app.librarium.controller;

import com.app.librarium.model.User;
import com.app.librarium.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthServiceImpl userService;

    @PostMapping("auth/register")
    @ResponseBody
    public ResponseEntity<Object> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("auth/login")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody User user) {
        return userService.login(user);
    }
}