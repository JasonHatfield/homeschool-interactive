package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import com.jasonhatfield.homeschoolinteractive.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing assignments.
 */
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Retrieves all assignments.
     *
     * @return the list of assignments
     */
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param assignmentId the ID of the assignment
     * @return the optional assignment
     */
    public Optional<Assignment> getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }

    /**
     * Retrieves assignments by student ID.
     *
     * @param studentId the ID of the student
     * @return the list of assignments
     */
    public List<Assignment> getAssignmentsByStudentId(Long studentId) {
        return assignmentRepository.findByStudentId(studentId);
    }

    /**
     * Saves an assignment.
     *
     * @param assignment the assignment to be saved
     * @return the saved assignment
     */
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    /**
     * Deletes an assignment by its ID.
     *
     * @param assignmentId the ID of the assignment to be deleted
     * @throws EntityNotFoundException if the assignment is not found
     */
    public void deleteAssignment(Long assignmentId) {
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new EntityNotFoundException("Assignment not found with ID: " + assignmentId);
        }
        assignmentRepository.deleteById(assignmentId);
    }

    /**
     * Updates an assignment by its ID.
     *
     * @param assignmentId      the ID of the assignment to be updated
     * @param assignmentDetails the updated assignment details
     * @return the optional updated assignment
     */
    public Optional<Assignment> updateAssignment(Long assignmentId, Assignment assignmentDetails) {
        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setTitle(assignmentDetails.getTitle());
                    assignment.setDescription(assignmentDetails.getDescription());
                    assignment.setDueDate(assignmentDetails.getDueDate());
                    assignment.setStatus(assignmentDetails.getStatus());
                    return assignmentRepository.save(assignment);
                });
    }

    /**
     * Updates the status of an assignment by its ID.
     *
     * @param assignmentId the ID of the assignment to update the status
     * @param status       the updated assignment status
     * @return the optional updated assignment
     */
    public Optional<Assignment> updateAssignmentStatus(Long assignmentId, AssignmentStatus status) {
        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setStatus(status);
                    return assignmentRepository.save(assignment);
                });
    }

    /**
     * Retrieves assignments between two dates.
     *
     * @param start the start date
     * @param end   the end date
     * @return the list of assignments between the dates
     */
    public List<Assignment> getAssignmentsBetweenDates(Date start, Date end) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        start = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        end = endCal.getTime();

        return assignmentRepository.findAllByDueDateBetween(start, end);
    }
}
