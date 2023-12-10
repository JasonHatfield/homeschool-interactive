package com.jasonhatfield.homeschoolinteractive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasonhatfield.homeschoolinteractive.model.Student;
import com.jasonhatfield.homeschoolinteractive.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains unit tests for the StudentController class.
 * It uses Mockito and MockMvc to mock dependencies and perform HTTP requests.
 */
@ExtendWith(MockitoExtension.class)
public class StudentControllerTests {

    private MockMvc mockMvc;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testAddNewStudent() throws Exception {
        Student newStudent = createTestStudent(null, "New", "Student", 9);
        given(studentService.saveStudent(any(Student.class))).willReturn(newStudent);

        String requestBody = objectMapper.writeValueAsString(newStudent);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(newStudent.getFirstName()));
    }

    @Test
    public void testRetrieveAllStudents() throws Exception {
        Student student1 = createTestStudent(1L, "John", "Doe", 10);
        Student student2 = createTestStudent(2L, "Jane", "Doe", 11);
        given(studentService.getAllStudents()).willReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Long id = 1L;
        Student student = createTestStudent(id, "John", "Doe", 10);
        given(studentService.getStudentById(id)).willReturn(Optional.of(student));

        mockMvc.perform(get("/students/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(id));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Long id = 1L;
        Student student = createTestStudent(id, "Updated", "Doe", 11);
        given(studentService.updateStudent(eq(id), any(Student.class))).willReturn(Optional.of(student));

        String requestBody = objectMapper.writeValueAsString(student);

        mockMvc.perform(put("/students/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/students/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateStudentWithMissingRequiredFields() throws Exception {
        Student invalidStudent = new Student(); // Missing firstName, lastName, and gradeLevel

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithInvalidData() throws Exception {
        Long id = 1L;
        Student invalidStudent = new Student(); // Missing firstName, lastName, and gradeLevel

        mockMvc.perform(put("/students/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());
    }

    private Student createTestStudent(Long id, String firstName, String lastName, int gradeLevel) {
        Student student = new Student();
        student.setStudentId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGradeLevel(gradeLevel);
        return student;
    }
}
