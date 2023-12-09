package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import com.jasonhatfield.homeschoolinteractive.repository.AssignmentRepository;
import com.jasonhatfield.homeschoolinteractive.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, SubjectRepository subjectRepository) {
        this.assignmentRepository = assignmentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

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

    public Optional<Assignment> updateAssignmentStatus(Long assignmentId, AssignmentStatus status) {
        return assignmentRepository.findById(assignmentId).map(assignment -> {
            assignment.setStatus(status);
            return assignmentRepository.save(assignment);
        });
    }

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
