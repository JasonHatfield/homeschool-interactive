package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Login successful");
    }
}
