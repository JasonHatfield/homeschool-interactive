package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class represents a controller for handling logout requests.
 */
@RestController
public class LogoutController {

    /**
     * Handles the logout request.
     * 
     * @param request  the HttpServletRequest object representing the incoming request
     * @param response the HttpServletResponse object representing the outgoing response
     * @return a String indicating the success of the logout operation
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "Logged out successfully";
    }
}
