package com.jasonhatfield.homeschoolinteractive.controller;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void getAllStudentsTest() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(new Student(), new Student()));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(studentService).getAllStudents();
    }

    @Test
    void getStudentByIdTest_Success() throws Exception {
        Student student = new Student();
        student.setId(1L);
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(studentService).getStudentById(1L);
    }

    @Test
    void getStudentByIdTest_NotFound() throws Exception {
        when(studentService.getStudentById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
        verify(studentService).getStudentById(any());
    }

    @Test
    void createStudentTest_Success() throws Exception {
        Student savedStudent = new Student();
        savedStudent.setId(1L);
        when(studentService.saveStudent(any(Student.class))).thenReturn(savedStudent);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"gradeLevel\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }


    @Test
    void deleteStudentTest_Success() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
        verify(studentService).deleteStudent(1L);
    }

    @Test
    void updateStudentTest_Success() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(Optional.of(updatedStudent));

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\", \"lastName\":\"Doe\", \"gradeLevel\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }


    @Test
    void updateStudentTest_NotFound() throws Exception {
        when(studentService.updateStudent(eq(999L), any(Student.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\", \"lastName\":\"Doe\", \"gradeLevel\":2}")) // Include gradeLevel
                .andExpect(status().isNotFound());
    }

    // Additional test cases as needed
}
