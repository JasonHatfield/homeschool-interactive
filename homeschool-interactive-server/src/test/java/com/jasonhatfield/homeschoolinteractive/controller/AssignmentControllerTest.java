package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.model.Assignment;
import com.jasonhatfield.homeschoolinteractive.service.AssignmentService;
import com.jasonhatfield.homeschoolinteractive.model.AssignmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Date;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AssignmentControllerTest {

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
    }

    @Test
    void getAllAssignments_ShouldReturnAssignments() throws Exception {
        Assignment mockAssignment = new Assignment();
        mockAssignment.setAssignmentId(1L); // Assuming the setter is available
        when(assignmentService.getAllAssignments()).thenReturn(List.of(mockAssignment));

        mockMvc.perform(get("/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assignmentId").value(mockAssignment.getAssignmentId()));
    }

    @Test
    void getAssignmentsBetween_ValidRange_ShouldReturnAssignments() throws Exception {
        List<Assignment> assignments = Collections.singletonList(new Assignment());
        when(assignmentService.getAssignmentsBetweenDates(any(), any())).thenReturn(assignments);

        mockMvc.perform(get("/assignments/range")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    void getAssignmentsBetween_InvalidRange_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/assignments/range")
                        .param("startDate", "2023-01-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAssignmentById_Found_ShouldReturnAssignment() throws Exception {
        Long assignmentId = 1L;
        Assignment mockAssignment = new Assignment();
        mockAssignment.setAssignmentId(assignmentId); // Assuming the setter is available
        when(assignmentService.getAssignmentById(assignmentId)).thenReturn(Optional.of(mockAssignment));

        mockMvc.perform(get("/assignments/{assignmentId}", assignmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(mockAssignment.getAssignmentId()));
    }

    @Test
    void getAssignmentById_NotFound_ShouldReturnNotFound() throws Exception {
        Long assignmentId = 1L;
        when(assignmentService.getAssignmentById(assignmentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/assignments/{assignmentId}", assignmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAssignmentsByStudentId_Found_ShouldReturnAssignments() throws Exception {
        Long studentId = 1L;
        List<Assignment> assignments = Collections.singletonList(new Assignment());
        when(assignmentService.getAssignmentsByStudentId(studentId)).thenReturn(assignments);

        mockMvc.perform(get("/assignments/student/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    void getAssignmentsByStudentId_NotFound_ShouldReturnNotFound() throws Exception {
        Long studentId = 1L;
        when(assignmentService.getAssignmentsByStudentId(studentId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/assignments/student/{studentId}", studentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAssignment_Valid_ShouldCreateAndReturnAssignment() throws Exception {
        Assignment mockAssignment = new Assignment();
        mockAssignment.setAssignmentId(1L);
        mockAssignment.setTitle("Test Assignment"); // Set title
        mockAssignment.setDescription("Test Description"); // Set description
        mockAssignment.setDueDate(new Date()); // Set a valid date
        mockAssignment.setStatus(AssignmentStatus.Incomplete);
        when(assignmentService.saveAssignment(any(Assignment.class))).thenReturn(mockAssignment);

        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Assignment\", \"description\": \"Test Description\", \"dueDate\": \"2023-01-01\", \"status\": \"Incomplete\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Assignment"));
    }

    @Test
    void deleteAssignment_Exists_ShouldReturnOk() throws Exception {
        Long assignmentId = 1L;
        doNothing().when(assignmentService).deleteAssignment(assignmentId);

        mockMvc.perform(delete("/assignments/{assignmentId}", assignmentId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAssignment_NotFound_ShouldReturnNotFound() throws Exception {
        Long assignmentId = 1L;
        doThrow(new EntityNotFoundException("Assignment not found with ID: " + assignmentId))
                .when(assignmentService).deleteAssignment(assignmentId);

        mockMvc.perform(delete("/assignments/{assignmentId}", assignmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAssignment_Valid_ShouldReturnUpdatedAssignment() throws Exception {
        Long assignmentId = 1L;
        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setAssignmentId(assignmentId);
        updatedAssignment.setTitle("Updated Title"); // Set updated title
        updatedAssignment.setDescription("Updated Description"); // Set updated description
        updatedAssignment.setDueDate(new Date());
        updatedAssignment.setStatus(AssignmentStatus.Incomplete);
        when(assignmentService.updateAssignment(eq(assignmentId), any(Assignment.class))).thenReturn(Optional.of(updatedAssignment));

        mockMvc.perform(put("/assignments/{assignmentId}", assignmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Title\", \"description\": \"Updated Description\", \"dueDate\": \"2023-01-01\", \"status\": \"Incomplete\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void updateAssignmentStatus_Valid_ShouldReturnUpdatedAssignment() throws Exception {
        Long assignmentId = 1L;
        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setAssignmentId(assignmentId);
        // Ensure all required fields of updatedAssignment are set, especially non-nullable fields
        updatedAssignment.setTitle("Some Title");
        updatedAssignment.setDescription("Some Description");
        updatedAssignment.setDueDate(new Date());
        updatedAssignment.setStatus(AssignmentStatus.Accepted);
        when(assignmentService.updateAssignmentStatus(eq(assignmentId), any())).thenReturn(Optional.of(updatedAssignment));

        mockMvc.perform(put("/assignments/{assignmentId}/status", assignmentId)
                        .param("status", "Accepted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Accepted"));
    }

    @Test
    void updateAssignmentStatus_InvalidStatus_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/assignments/{assignmentId}/status", 1L)
                        .param("status", "INVALID_STATUS"))
                .andExpect(status().isBadRequest());
    }

}
