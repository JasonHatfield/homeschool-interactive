package com.jasonhatfield.homeschoolinteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasonhatfield.homeschoolinteractive.model.Student;

/**
 * This interface represents a repository for managing student entities.
 * It extends the JpaRepository interface, providing CRUD operations for the Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
