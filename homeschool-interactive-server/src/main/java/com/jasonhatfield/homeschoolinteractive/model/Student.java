package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents a student in the homeschool interactive system.
 * This class contains information about the student such as their ID, grade level, first name, and last name.
 */

@Setter
@Getter
@Entity
@Table(name = "Students")
public class Student {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Grade level cannot be null")
    @Column(name = "grade_level")
    private Integer gradeLevel;

    @Column(name = "last_day")
    private Date lastDay;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters")
    @Column(name = "last_name")
    private String lastName;

}
