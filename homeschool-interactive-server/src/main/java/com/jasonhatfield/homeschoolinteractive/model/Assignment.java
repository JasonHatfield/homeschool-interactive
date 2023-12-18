package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents an assignment in the Homeschool Interactive system.
 * An assignment is a task or piece of work that is assigned to a student for a specific subject.
 */

@Getter
@Setter
@Entity
@Table(name = "Assignments")
public class Assignment {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, message = "Description must not be empty")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Due date cannot be null")
    @Temporal(TemporalType.DATE)
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

}
