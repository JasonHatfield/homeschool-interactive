package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController class handles HTTP requests related to user operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a new UserController with the specified UserService.
     *
     * @param userService the UserService to be used
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Deletes a user with the specified username.
     *
     * @param username the username of the user to be deleted
     * @return a ResponseEntity with the result of the deletion
     */
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (!userService.existsByUsername(username)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: Username not found!");
        }

        userService.deleteUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

}
