package com.jasonhatfield.homeschoolinteractive.repository;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * This interface represents a repository for managing assignments in the application.
 * It extends the JpaRepository interface, providing CRUD operations for the Assignment entity.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Retrieves a list of assignments with due dates between the specified start and end dates.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of assignments with due dates between the start and end dates
     */
    @Query("SELECT a FROM Assignment a WHERE a.dueDate >= :startDate AND a.dueDate <= :endDate")
    List<Assignment> findAllByDueDateBetween(Date startDate, Date endDate);

    /**
     * Retrieves a list of assignments associated with the specified student ID.
     *
     * @param studentId the ID of the student
     * @return a list of assignments associated with the student ID
     */
    List<Assignment> findByStudentId(Long studentId);

}
