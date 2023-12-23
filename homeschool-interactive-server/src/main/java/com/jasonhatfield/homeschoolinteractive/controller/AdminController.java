package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AdminController class handles requests related to admin operations.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * Retrieves the admin information.
     *
     * @return The admin information as a string.
     */
    @GetMapping()
    public String getAdminInfo() {
        return "Admin Information";
    }

}
