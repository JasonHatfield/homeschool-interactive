package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Student;
import com.jasonhatfield.homeschoolinteractive.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing students.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructs a StudentService with the specified StudentRepository.
     *
     * @param studentRepository the student repository
     */
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all students.
     *
     * @return the list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param id the student ID
     * @return the optional student
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * Saves a new student.
     *
     * @param student the student to be saved
     * @return the saved student
     */
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id the student ID
     */
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Updates a student by their ID.
     *
     * @param id            the student ID
     * @param studentDetails the updated student details
     * @return the optional updated student
     */
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(studentDetails.getFirstName());
                    student.setLastName(studentDetails.getLastName());
                    student.setGradeLevel(studentDetails.getGradeLevel());

                    return studentRepository.save(student);
                });
    }

}
