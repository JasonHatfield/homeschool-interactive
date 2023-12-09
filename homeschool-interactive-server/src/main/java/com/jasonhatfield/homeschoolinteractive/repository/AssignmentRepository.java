package com.jasonhatfield.homeschoolinteractive.repository;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    @Query("SELECT a FROM Assignment a WHERE a.dueDate >= :startDate AND a.dueDate <= :endDate")
    List<Assignment> findAllByDueDateBetween(Date startDate, Date endDate);
}
