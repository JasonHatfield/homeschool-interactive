package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * Represents a student in the homeschool interactive system.
 * This class contains information about the student such as their ID, grade level, first name, and last name.
 */
@Entity
@Table(name = "Students")
public class Student {

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

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Date getLastDay() {
        return lastDay;
    }

    public void setLastDay(Date lastDay) {
        this.lastDay = lastDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
