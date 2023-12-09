package com.jasonhatfield.homeschoolinteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jasonhatfield.homeschoolinteractive.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
