package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a subject in the homeschool interactive system.
 * Each subject has a unique identifier (subjectId) and a name.
 */
@Entity
@Table(name = "Subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Retrieves the unique identifier of the subject.
     *
     * @return The subject's identifier.
     */
    public Long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the unique identifier of the subject.
     *
     * @param subjectId The subject's identifier.
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Retrieves the name of the subject.
     *
     * @return The subject's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subject.
     *
     * @param name The subject's name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
