package com.jasonhatfield.homeschoolinteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasonhatfield.homeschoolinteractive.model.Subject;

/**
 * This interface represents a repository for managing Subject entities.
 * It extends the JpaRepository interface, providing CRUD operations for the Subject entity.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
