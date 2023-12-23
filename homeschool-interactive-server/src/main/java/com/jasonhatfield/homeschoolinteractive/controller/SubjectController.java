package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Subject;
import com.jasonhatfield.homeschoolinteractive.service.SubjectService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing subjects.
 */
@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Constructor for SubjectController.
     *
     * @param subjectService the subject service
     */
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Get all subjects.
     *
     * @return the list of subjects
     */
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    /**
     * Get a subject by ID.
     *
     * @param id the ID of the subject
     * @return the subject if found, otherwise return 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new subject.
     *
     * @param subject the subject to be created
     * @return the created subject with HTTP status 201 Created
     */
    @PostMapping
    public ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject subject) {
        Subject savedSubject = subjectService.saveSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
    }

    /**
     * Delete a subject by ID.
     *
     * @param id the ID of the subject to be deleted
     * @return HTTP status 200 OK if the subject is deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update a subject by ID.
     *
     * @param id             the ID of the subject to be updated
     * @param subjectDetails the updated subject details
     * @return the updated subject if found, otherwise return 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody Subject subjectDetails) {
        return subjectService.updateSubject(id, subjectDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
