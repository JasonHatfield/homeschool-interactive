package com.jasonhatfield.homeschoolinteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasonhatfield.homeschoolinteractive.model.Student;

/**
 * This interface represents a repository for managing Student entities.
 * It extends the JpaRepository interface, which provides basic CRUD operations for the Student entity.
 * The StudentRepository interface is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
