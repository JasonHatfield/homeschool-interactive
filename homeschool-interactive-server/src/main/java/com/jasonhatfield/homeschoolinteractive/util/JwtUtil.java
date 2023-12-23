package com.jasonhatfield.homeschoolinteractive.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 */
public class JwtUtil {

    private static final String SECRET_KEY = "my-name-is-jason-hatfield"; // Replace with a real secret key
    private static final long EXPIRATION_TIME = 900_000; // 15 minutes

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username for which to generate the token.
     * @return The generated JWT token.
     */
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The extracted username.
     */
    public static String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The user details against which to validate the token.
     * @return true if the token is valid, false otherwise.
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token) {
        return getDecodedJWT(token).getExpiresAt().before(new Date());
    }

    private static DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build();
        return verifier.verify(token);
    }
}
