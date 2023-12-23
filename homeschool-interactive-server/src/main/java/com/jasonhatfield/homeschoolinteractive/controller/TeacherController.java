package com.jasonhatfield.homeschoolinteractive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class represents the controller for managing teacher-related operations.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    /**
     * Retrieves the information of a teacher.
     *
     * @return the teacher information as a string
     */
    @GetMapping()
    public String getTeacherInfo() {
        // Replace with actual logic
        return "Teacher Information";
    }

}
