package com.jasonhatfield.homeschoolinteractive.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getAdminInfo_ShouldReturnAdminInformation() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Information"));
    }
}
