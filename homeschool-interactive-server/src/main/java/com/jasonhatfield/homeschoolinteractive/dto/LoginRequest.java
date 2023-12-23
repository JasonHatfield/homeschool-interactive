package com.jasonhatfield.homeschoolinteractive.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a login request.
 */
@Setter
@Getter
public class LoginRequest {
    /**
     * The username for the login request.
     */
    private String username;

    /**
     * The password for the login request.
     */
    private String password;
}
