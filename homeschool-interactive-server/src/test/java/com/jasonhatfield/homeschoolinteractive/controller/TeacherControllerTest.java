package com.jasonhatfield.homeschoolinteractive.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the TeacherController class.
 */
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    /**
     * Test case for getting teacher information.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getTeacherInfoTest() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher Information"));
    }

}
