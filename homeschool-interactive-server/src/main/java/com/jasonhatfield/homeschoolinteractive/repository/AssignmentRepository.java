package com.jasonhatfield.homeschoolinteractive.repository;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * This interface represents a repository for managing assignments.
 * It extends the JpaRepository interface, providing CRUD operations for the Assignment entity.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    /**
     * Retrieves a list of assignments with due dates between the specified start and end dates.
     * 
     * @param startDate The start date for filtering assignments.
     * @param endDate The end date for filtering assignments.
     * @return A list of assignments with due dates between the start and end dates.
     */
    @Query("SELECT a FROM Assignment a WHERE a.dueDate >= :startDate AND a.dueDate <= :endDate")
    List<Assignment> findAllByDueDateBetween(Date startDate, Date endDate);
}
