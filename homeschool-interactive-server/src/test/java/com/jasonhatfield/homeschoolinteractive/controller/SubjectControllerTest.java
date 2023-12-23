package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Subject;
import com.jasonhatfield.homeschoolinteractive.service.SubjectService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the SubjectController class.
 */
@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    /**
     * Test case for getting all subjects.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getAllSubjectsTest() throws Exception {
        Subject subject1 = new Subject();
        subject1.setSubjectId(1L);
        subject1.setName("Math");

        Subject subject2 = new Subject();
        subject2.setSubjectId(2L);
        subject2.setName("Science");

        when(subjectService.getAllSubjects()).thenReturn(Arrays.asList(subject1, subject2));

        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].subjectId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Math")))
                .andExpect(jsonPath("$[1].subjectId", is(2)))
                .andExpect(jsonPath("$[1].name", is("Science")));

        verify(subjectService).getAllSubjects();
    }

    /**
     * Test case for getting a subject by ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getSubjectByIdTest_Success() throws Exception {
        Subject subject = new Subject();
        subject.setSubjectId(1L);
        subject.setName("Math");

        when(subjectService.getSubjectById(1L)).thenReturn(Optional.of(subject));

        mockMvc.perform(get("/subjects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectId", is(1)))
                .andExpect(jsonPath("$.name", is("Math")));

        verify(subjectService).getSubjectById(1L);
    }

    /**
     * Test case for getting a subject by ID when it is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getSubjectByIdTest_NotFound() throws Exception {
        when(subjectService.getSubjectById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/subjects/999"))
                .andExpect(status().isNotFound());

        verify(subjectService).getSubjectById(anyLong());
    }

    /**
     * Test case for creating a subject.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void createSubjectTest_Success() throws Exception {
        Subject subject = new Subject();
        subject.setSubjectId(1L);
        subject.setName("Math");

        when(subjectService.saveSubject(any(Subject.class))).thenReturn(subject);

        mockMvc.perform(post("/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Math\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subjectId", is(1)))
                .andExpect(jsonPath("$.name", is("Math")));

        verify(subjectService).saveSubject(any(Subject.class));
    }

    /**
     * Test case for deleting a subject.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void deleteSubjectTest_Success() throws Exception {
        doNothing().when(subjectService).deleteSubject(1L);

        mockMvc.perform(delete("/subjects/1"))
                .andExpect(status().isOk());

        verify(subjectService).deleteSubject(1L);
    }

    /**
     * Test case for updating a subject.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void updateSubjectTest_Success() throws Exception {
        Subject updatedSubject = new Subject();
        updatedSubject.setSubjectId(1L);
        updatedSubject.setName("Advanced Math");

        when(subjectService.updateSubject(eq(1L), any(Subject.class))).thenReturn(Optional.of(updatedSubject));

        mockMvc.perform(put("/subjects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Advanced Math\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectId", is(1)))
                .andExpect(jsonPath("$.name", is("Advanced Math")));

        verify(subjectService).updateSubject(eq(1L), any(Subject.class));
    }

    /**
     * Test case for updating a subject when it is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void updateSubjectTest_NotFound() throws Exception {
        when(subjectService.updateSubject(eq(999L), any(Subject.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/subjects/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Advanced Math\"}"))
                .andExpect(status().isNotFound());

        verify(subjectService).updateSubject(eq(999L), any(Subject.class));
    }

}
