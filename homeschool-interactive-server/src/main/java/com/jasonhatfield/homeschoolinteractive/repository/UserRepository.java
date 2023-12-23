package com.jasonhatfield.homeschoolinteractive.repository;

import com.jasonhatfield.homeschoolinteractive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * This interface represents a repository for managing User entities.
 * It extends the JpaRepository interface, providing CRUD operations for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the found user, or an empty Optional if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     */
    void deleteByUsername(String username);
}
