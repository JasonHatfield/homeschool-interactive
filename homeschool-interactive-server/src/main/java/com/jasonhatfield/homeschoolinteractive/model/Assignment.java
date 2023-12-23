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

/**
 * Represents an assignment in the homeschool interactive system.
 */
@Getter
@Setter
@Entity
@Table(name = "Assignments")
public class Assignment {

    // Getters and Setters

    /**
     * Gets the assignment ID.
     *
     * @return The assignment ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    /**
     * Gets the subject of the assignment.
     *
     * @return The subject of the assignment.
     */
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    /**
     * Gets the title of the assignment.
     *
     * @return The title of the assignment.
     */
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(nullable = false)
    private String title;

    /**
     * Gets the description of the assignment.
     *
     * @return The description of the assignment.
     */
    @NotNull(message = "Description cannot be null")
    @Size(min = 1, message = "Description must not be empty")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * Gets the due date of the assignment.
     *
     * @return The due date of the assignment.
     */
    @NotNull(message = "Due date cannot be null")
    @Temporal(TemporalType.DATE)
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    /**
     * Gets the status of the assignment.
     *
     * @return The status of the assignment.
     */
    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

    /**
     * Gets the student associated with the assignment.
     *
     * @return The student associated with the assignment.
     */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

}
