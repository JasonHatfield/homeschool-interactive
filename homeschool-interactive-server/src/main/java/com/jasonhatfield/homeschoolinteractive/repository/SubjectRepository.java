package com.jasonhatfield.homeschoolinteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasonhatfield.homeschoolinteractive.model.Subject;

/**
 * Repository interface for managing Subject entities.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
