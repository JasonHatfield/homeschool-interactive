package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import com.jasonhatfield.homeschoolinteractive.repository.AssignmentRepository;
import com.jasonhatfield.homeschoolinteractive.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;

    /**
     * Constructs an AssignmentService object with the given repositories.
     *
     * @param assignmentRepository The repository for managing assignments.
     * @param subjectRepository    The repository for managing subjects.
     */
    public AssignmentService(AssignmentRepository assignmentRepository, SubjectRepository subjectRepository) {
        this.assignmentRepository = assignmentRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Retrieves all assignments.
     *
     * @return A list of all assignments.
     */
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param assignmentId The ID of the assignment to retrieve.
     * @return An Optional containing the assignment, or an empty Optional if not found.
     */
    public Optional<Assignment> getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }

    /**
     * Saves an assignment.
     *
     * @param assignment The assignment to save.
     * @return The saved assignment.
     */
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    /**
     * Deletes an assignment by its ID.
     *
     * @param assignmentId The ID of the assignment to delete.
     * @throws EntityNotFoundException if the assignment with the given ID does not exist.
     */
    public void deleteAssignment(Long assignmentId) {
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new EntityNotFoundException("Assignment not found with ID: " + assignmentId);
        }
        assignmentRepository.deleteById(assignmentId);
    }

    /**
     * Updates an assignment with the given ID and assignment details.
     *
     * @param assignmentId      The ID of the assignment to update.
     * @param assignmentDetails The updated assignment details.
     * @return An Optional containing the updated assignment, or an empty Optional if not found.
     */
    public Optional<Assignment> updateAssignment(Long assignmentId, Assignment assignmentDetails) {
        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setTitle(assignmentDetails.getTitle());
                    assignment.setDescription(assignmentDetails.getDescription());
                    assignment.setDueDate(assignmentDetails.getDueDate());
                    assignment.setStatus(assignmentDetails.getStatus());

                    if (assignmentDetails.getSubject() != null
                            && assignmentDetails.getSubject().getSubjectId() != null) {
                        subjectRepository.findById(assignmentDetails.getSubject().getSubjectId())
                                .ifPresent(assignment::setSubject);
                    }

                    return assignmentRepository.save(assignment);
                });
    }

    /**
     * Updates the status of an assignment with the given ID.
     *
     * @param assignmentId The ID of the assignment to update.
     * @param status       The updated assignment status.
     * @return An Optional containing the updated assignment, or an empty Optional if not found.
     */
    public Optional<Assignment> updateAssignmentStatus(Long assignmentId, AssignmentStatus status) {
        return assignmentRepository.findById(assignmentId).map(assignment -> {
            assignment.setStatus(status);
            return assignmentRepository.save(assignment);
        });
    }

    /**
     * Retrieves assignments that have due dates between the specified start and end dates.
     *
     * @param start The start date.
     * @param end   The end date.
     * @return A list of assignments with due dates between the start and end dates.
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
