package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Student;
import com.jasonhatfield.homeschoolinteractive.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the controller for managing student-related operations.
 * It handles HTTP requests related to students and interacts with the StudentService.
 */
@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentService studentService;

    /**
     * Constructs a new StudentController with the given StudentService.
     *
     * @param studentService the StudentService used to perform student-related operations
     */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Retrieves a list of all students.
     *
     * @return a list of all students
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param id the ID of the student to retrieve
     * @return the ResponseEntity containing the student if found, or a not found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new student.
     *
     * @param student the student to create
     * @return the ResponseEntity containing the created student
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete
     * @return the ResponseEntity indicating the success of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates a student by their ID.
     *
     * @param id             the ID of the student to update
     * @param studentDetails the updated student details
     * @return the ResponseEntity containing the updated student if found, or a not found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
