package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.User;
import com.jasonhatfield.homeschoolinteractive.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserService with the specified UserRepository and PasswordEncoder.
     *
     * @param userRepository the repository for managing user data
     * @param passwordEncoder the encoder for encoding user passwords
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user by encoding the password and saving the user to the repository.
     *
     * @param user the user to be registered
     */
    public void registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Checks if a user with the specified username exists in the repository.
     *
     * @param username the username to check
     * @return true if a user with the specified username exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Deletes a user with the specified username from the repository.
     *
     * @param username the username of the user to be deleted
     */
    @Transactional
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
