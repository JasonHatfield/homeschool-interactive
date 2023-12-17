package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    // Example endpoint
    @GetMapping()
    public String getTeacherInfo() {
        // Replace with actual logic
        return "Teacher Information";
    }

    // Add more methods as needed
}
