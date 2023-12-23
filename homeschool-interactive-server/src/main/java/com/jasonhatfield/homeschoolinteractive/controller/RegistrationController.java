package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.User;
import com.jasonhatfield.homeschoolinteractive.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The RegistrationController class handles the registration of new users.
 * It provides an endpoint for registering a new user and delegates the registration process to the UserService.
 */
@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;

    /**
     * Constructs a new RegistrationController with the specified UserService.
     *
     * @param userService the UserService used for user registration
     */
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user by invoking the registerNewUser method of the UserService.
     *
     * @param user the User object representing the user to be registered
     * @return a ResponseEntity with a success message if the user is registered successfully
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.registerNewUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
