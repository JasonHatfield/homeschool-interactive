package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Student;
import com.jasonhatfield.homeschoolinteractive.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the service layer for managing student data.
 * It provides methods for retrieving, saving, updating, and deleting student records.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructs a new StudentService with the given StudentRepository.
     * @param studentRepository the repository used for accessing student data
     */
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all students from the repository.
     * @return a list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by their ID from the repository.
     * @param id the ID of the student to retrieve
     * @return an Optional containing the student, or an empty Optional if no student with the given ID exists
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * Saves a new student to the repository.
     * @param student the student to save
     * @return the saved student
     */
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Deletes a student from the repository by their ID.
     * @param id the ID of the student to delete
     */
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Updates a student in the repository with the given ID and details.
     * @param id the ID of the student to update
     * @param studentDetails the updated details of the student
     * @return an Optional containing the updated student, or an empty Optional if no student with the given ID exists
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
