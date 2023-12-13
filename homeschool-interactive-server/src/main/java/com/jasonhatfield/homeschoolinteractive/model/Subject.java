package com.jasonhatfield.homeschoolinteractive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a subject in the homeschool interactive system.
 * Each subject has a unique identifier (subjectId) and a name.
 */
@Setter
@Getter
@Entity
@Table(name = "Subjects")
public class Subject {

    /**
     * -- GETTER --
     *  Retrieves the unique identifier of the subject.
     *
     *
     * -- SETTER --
     *  Sets the unique identifier of the subject.
     *
     @return The subject's identifier.
      * @param subjectId The subject's identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    /**
     * -- GETTER --
     *  Retrieves the name of the subject.
     *
     *
     * -- SETTER --
     *  Sets the name of the subject.
     *
     @return The subject's name.
      * @param name The subject's name.
     */
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

}
