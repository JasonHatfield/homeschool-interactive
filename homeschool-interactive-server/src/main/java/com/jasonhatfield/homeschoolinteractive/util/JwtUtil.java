package com.jasonhatfield.homeschoolinteractive.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "my-name-is-jason-hatfield"; // Replace with a real secret key
    private static final long EXPIRATION_TIME = 900_000; // 15 minutes

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    // Additional methods for token validation can be added here
}

