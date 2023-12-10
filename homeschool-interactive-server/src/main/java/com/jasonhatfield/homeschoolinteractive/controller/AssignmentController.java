package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import com.jasonhatfield.homeschoolinteractive.service.AssignmentService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * The AssignmentController class handles HTTP requests related to assignments.
 * It provides endpoints for retrieving, creating, updating, and deleting assignments.
 */
@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Constructs a new AssignmentController with the given AssignmentService.
     * 
     * @param assignmentService the AssignmentService used to perform assignment-related operations
     */
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * Retrieves all assignments.
     * 
     * @return a list of all assignments
     */
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * Retrieves assignments between the specified start and end dates.
     * 
     * @param startDate the start date of the range (optional)
     * @param endDate the end date of the range (optional)
     * @return a ResponseEntity containing a list of assignments within the specified range,
     *         or a bad request response if the start and end dates are not provided
     */
    @GetMapping("/range")
    public ResponseEntity<List<Assignment>> getAssignmentsBetween(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        if (startDate != null && endDate != null) {
            List<Assignment> assignments = assignmentService.getAssignmentsBetweenDates(startDate, endDate);
            return ResponseEntity.ok(assignments);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves the assignment with the specified ID.
     * 
     * @param assignmentId the ID of the assignment
     * @return a ResponseEntity containing the assignment if found, or a not found response if not found
     */
    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long assignmentId) {
        return assignmentService.getAssignmentById(assignmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new assignment.
     * 
     * @param assignment the assignment to be created
     * @return a ResponseEntity containing the created assignment
     */
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@Valid @RequestBody Assignment assignment) {
        Assignment savedAssignment = assignmentService.saveAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
    }

    /**
     * Deletes the assignment with the specified ID.
     * 
     * @param assignmentId the ID of the assignment to be deleted
     * @return a ResponseEntity indicating the success or failure of the deletion
     */
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long assignmentId) {
        try {
            assignmentService.deleteAssignment(assignmentId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates the assignment with the specified ID.
     * 
     * @param assignmentId the ID of the assignment to be updated
     * @param assignmentDetails the updated details of the assignment
     * @return a ResponseEntity containing the updated assignment if found, or a not found response if not found
     */
    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long assignmentId,
            @Valid @RequestBody Assignment assignmentDetails) {
        return assignmentService.updateAssignment(assignmentId, assignmentDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates the status of the assignment with the specified ID.
     * 
     * @param assignmentId the ID of the assignment to update the status for
     * @param statusValue the new status value for the assignment
     * @return a ResponseEntity containing the updated assignment if found and the status value is valid,
     *         or a not found response if not found, or a bad request response if the status value is invalid
     */
    @PutMapping("/{assignmentId}/status")
    public ResponseEntity<Assignment> updateAssignmentStatus(@PathVariable Long assignmentId,
            @RequestParam("status") String statusValue) {
        try {
            AssignmentStatus status = AssignmentStatus.valueOf(statusValue);
            return assignmentService.updateAssignmentStatus(assignmentId, status)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
