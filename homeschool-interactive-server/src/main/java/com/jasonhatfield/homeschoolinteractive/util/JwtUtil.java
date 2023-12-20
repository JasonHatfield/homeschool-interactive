package com.jasonhatfield.homeschoolinteractive.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtil {

    private static final String SECRET_KEY = "my-name-is-jason-hatfield"; // Replace with a real secret key
    private static final long EXPIRATION_TIME = 900_000; // 15 minutes

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public static String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

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
