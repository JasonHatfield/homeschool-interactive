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

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Constructor for AssignmentController.
     *
     * @param assignmentService the assignment service
     */
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * Get all assignments.
     *
     * @return the list of all assignments
     */
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * Get assignments between specified dates.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list of assignments between the specified dates
     */
    @GetMapping("/range")
    public ResponseEntity<List<Assignment>> getAssignmentsBetween(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        if (startDate != null && endDate != null) {
            List<Assignment> assignments = assignmentService.getAssignmentsBetweenDates(startDate, endDate);
            return ResponseEntity.ok(assignments);
        }
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * Get assignment by ID.
     *
     * @param assignmentId the assignment ID
     * @return the assignment with the specified ID
     */
    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long assignmentId) {
        return assignmentService.getAssignmentById(assignmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get assignments by student ID.
     *
     * @param studentId the student ID
     * @return the list of assignments for the specified student ID
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByStudentId(@PathVariable Long studentId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByStudentId(studentId);
        if (assignments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignments);
    }

    /**
     * Create a new assignment.
     *
     * @param assignment the assignment to create
     * @return the created assignment
     */
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@Valid @RequestBody Assignment assignment) {
        Assignment savedAssignment = assignmentService.saveAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
    }

    /**
     * Delete an assignment by ID.
     *
     * @param assignmentId the assignment ID
     * @return the response entity
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
     * Update an assignment by ID.
     *
     * @param assignmentId      the assignment ID
     * @param assignmentDetails the updated assignment details
     * @return the updated assignment
     */
    @PutMapping("/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long assignmentId,
                                                       @Valid @RequestBody Assignment assignmentDetails) {
        return assignmentService.updateAssignment(assignmentId, assignmentDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update assignment status by ID.
     *
     * @param assignmentId the assignment ID
     * @param statusValue  the new assignment status value
     * @return the updated assignment
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
