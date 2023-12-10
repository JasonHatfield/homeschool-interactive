package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Subject;
import com.jasonhatfield.homeschoolinteractive.service.SubjectService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The SubjectController class handles HTTP requests related to subjects.
 * It provides endpoints for retrieving, creating, updating, and deleting subjects.
 */
@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Constructs a new SubjectController with the given SubjectService.
     * @param subjectService the SubjectService to be used for handling subject-related operations
     */
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Retrieves all subjects.
     * @return a list of all subjects
     */
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    /**
     * Retrieves a subject by its ID.
     * @param id the ID of the subject to retrieve
     * @return the subject with the specified ID if found, otherwise returns a not found response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new subject.
     * @param subject the subject to be created
     * @return the created subject with a status of CREATED
     */
    @PostMapping
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject subject) {
        Subject savedSubject = subjectService.saveSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
    }

    /**
     * Deletes a subject by its ID.
     * @param id the ID of the subject to delete
     * @return a success response if the subject was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates a subject by its ID.
     * @param id the ID of the subject to update
     * @param subjectDetails the updated details of the subject
     * @return the updated subject if found, otherwise returns a not found response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody Subject subjectDetails) {
        return subjectService.updateSubject(id, subjectDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
