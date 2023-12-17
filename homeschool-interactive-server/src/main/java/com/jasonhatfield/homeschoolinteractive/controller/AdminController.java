package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Example endpoint
    @GetMapping()
    public String getAdminInfo() {
        // Replace with actual logic
        return "Admin Information";
    }

}
