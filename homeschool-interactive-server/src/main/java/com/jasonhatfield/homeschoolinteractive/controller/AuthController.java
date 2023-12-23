package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.dto.AuthResponse;
import com.jasonhatfield.homeschoolinteractive.dto.LoginRequest;
import com.jasonhatfield.homeschoolinteractive.security.UserDetailsImpl;
import com.jasonhatfield.homeschoolinteractive.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

/**
 * The AuthController class handles authentication-related requests.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new AuthController with the specified AuthenticationManager.
     *
     * @param authenticationManager the AuthenticationManager used for user authentication
     */
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates the user with the provided login credentials.
     *
     * @param loginRequest the LoginRequest object containing the user's login credentials
     * @return a ResponseEntity containing the authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Get the user details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_UNKNOWN");

            // Generate JWT token
            String token = JwtUtil.generateToken(loginRequest.getUsername());

            // Get the user ID
            Long userId = userDetails.getUserId(); // Assuming UserDetailsImpl contains a method getUserId()

            // Return the authentication response
            return ResponseEntity.ok(new AuthResponse(token, role, userId));
        } catch (Exception e) {
            // Return authentication failure response
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
