package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents a student in the homeschool interactive system.
 */
@Setter
@Getter
@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id") // Ensure the name matches "id"
    private Long id; // Updated to "id" to match the corrected field name

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
