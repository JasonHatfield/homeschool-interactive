package com.jasonhatfield.homeschoolinteractive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import com.jasonhatfield.homeschoolinteractive.service.AssignmentService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * This class contains unit tests for the AssignmentController class.
 * It uses Mockito and MockMvc to mock dependencies and perform HTTP requests.
 * Each test method tests a specific functionality of the AssignmentController.
 */
@ExtendWith(MockitoExtension.class)
public class AssignmentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
    }

    @Test
    public void testCreateAssignment() throws Exception {
        Assignment newAssignment = new Assignment();
        newAssignment.setTitle("Sample Assignment");
        newAssignment.setDescription("Description of the Assignment");
        newAssignment.setDueDate(new Date()); // Set a valid date
        newAssignment.setStatus(AssignmentStatus.Incomplete); // Set a valid status

        given(assignmentService.saveAssignment(any(Assignment.class))).willReturn(newAssignment);

        mockMvc.perform(post("/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAssignment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(newAssignment.getTitle()));
    }

    @Test
    public void testRetrieveAllAssignments() throws Exception {
        Assignment assignment1 = new Assignment(); // Set properties
        Assignment assignment2 = new Assignment(); // Set properties
        given(assignmentService.getAllAssignments()).willReturn(Arrays.asList(assignment1, assignment2));

        mockMvc.perform(get("/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetAssignmentById() throws Exception {
        Long id = 1L;
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(id); // Set assignmentId

        given(assignmentService.getAssignmentById(id)).willReturn(Optional.of(assignment));

        mockMvc.perform(get("/assignments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(id));
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        Long id = 1L;
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(id);
        assignment.setTitle("Updated Title"); // Ensure this field is populated
        assignment.setDescription("Updated Description"); // Ensure this field is populated
        assignment.setDueDate(new Date()); // Ensure a valid date is set
        assignment.setStatus(AssignmentStatus.Incomplete); // Ensure a valid status is set

        given(assignmentService.updateAssignment(eq(id), any(Assignment.class))).willReturn(Optional.of(assignment));

        String requestBody = objectMapper.writeValueAsString(assignment);

        mockMvc.perform(put("/assignments/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(id));
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/assignments/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAssignmentsBetweenDates() throws Exception {
        mockMvc.perform(get("/assignments/range")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-01-31"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAssignmentStatus() throws Exception {
        Long id = 1L;
        String status = "Incomplete";
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(id);
        assignment.setStatus(AssignmentStatus.valueOf(status));

        given(assignmentService.updateAssignmentStatus(eq(id), any(AssignmentStatus.class)))
                .willReturn(Optional.of(assignment));

        mockMvc.perform(put("/assignments/" + id + "/status")
                .param("status", status))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAssignmentWithInvalidId() throws Exception {
        Long invalidId = 999L; // An ID that does not exist
        given(assignmentService.getAssignmentById(invalidId)).willReturn(Optional.empty());

        mockMvc.perform(get("/assignments/" + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateAssignmentWithInvalidId() throws Exception {
        Long invalidId = 999L;
        Assignment assignmentToUpdate = new Assignment();
        assignmentToUpdate.setTitle("Nonexistent");
        assignmentToUpdate.setDescription("This assignment does not exist");
        assignmentToUpdate.setDueDate(new Date());
        assignmentToUpdate.setStatus(AssignmentStatus.Incomplete);

        given(assignmentService.updateAssignment(eq(invalidId), any(Assignment.class))).willReturn(Optional.empty());

        mockMvc.perform(put("/assignments/" + invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentToUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAssignmentWithInvalidId() throws Exception {
        Long invalidId = 999L;
        doThrow(new EntityNotFoundException("Assignment not found")).when(assignmentService)
                .deleteAssignment(invalidId);

        mockMvc.perform(delete("/assignments/" + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAssignmentWithInvalidData() throws Exception {
        Assignment invalidAssignment = new Assignment(); // Missing required fields

        mockMvc.perform(post("/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidAssignment)))
                .andExpect(status().isBadRequest());
    }

}
