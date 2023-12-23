package com.jasonhatfield.homeschoolinteractive.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the response object for authentication.
 */
@Setter
@Getter
public class AuthResponse {
    private String token;
    private String role;
    private Long userId; // Add userId

    /**
     * Constructs a new AuthResponse object with the specified token, role, and userId.
     *
     * @param token  the authentication token
     * @param role   the role of the user
     * @param userId the ID of the user
     */
    public AuthResponse(String token, String role, Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }
}
