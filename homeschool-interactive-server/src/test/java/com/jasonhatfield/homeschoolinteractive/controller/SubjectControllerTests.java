package com.jasonhatfield.homeschoolinteractive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains unit tests for the SubjectController class.
 * It uses Mockito and MockMvc to mock dependencies and perform HTTP requests.
 */
@ExtendWith(MockitoExtension.class)
public class SubjectControllerTests {

    private MockMvc mockMvc;
    @Mock
    private SubjectService subjectService;
    @InjectMocks
    private SubjectController subjectController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    public void testCreateSubject() throws Exception {
        Subject subject = new Subject();
        subject.setName("Mathematics");
        given(subjectService.saveSubject(any(Subject.class))).willReturn(subject);

        mockMvc.perform(post("/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subject)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mathematics"));
    }

    @Test
    public void testRetrieveAllSubjects() throws Exception {
        Subject subject1 = new Subject();
        subject1.setName("Mathematics");
        Subject subject2 = new Subject();
        subject2.setName("Science");
        given(subjectService.getAllSubjects()).willReturn(Arrays.asList(subject1, subject2));

        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetSubjectById() throws Exception {
        Long id = 1L;
        Subject subject = new Subject();
        subject.setSubjectId(id);
        subject.setName("Mathematics");
        given(subjectService.getSubjectById(id)).willReturn(Optional.of(subject));

        mockMvc.perform(get("/subjects/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectId").value(id));
    }

    @Test
    public void testUpdateSubject() throws Exception {
        Long id = 1L;
        Subject subject = new Subject();
        subject.setSubjectId(id);
        subject.setName("Updated Subject");
        given(subjectService.updateSubject(eq(id), any(Subject.class))).willReturn(Optional.of(subject));

        mockMvc.perform(put("/subjects/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Subject"));
    }

    @Test
    public void testCreateSubjectWithInvalidData() throws Exception {
        Subject invalidSubject = new Subject(); // Name is missing

        mockMvc.perform(post("/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSubject)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateSubjectWithInvalidData() throws Exception {
        Long id = 1L;
        Subject invalidSubject = new Subject(); // Name is missing

        mockMvc.perform(put("/subjects/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSubject)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteSubject() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/subjects/" + id))
                .andExpect(status().isOk());
    }
}
