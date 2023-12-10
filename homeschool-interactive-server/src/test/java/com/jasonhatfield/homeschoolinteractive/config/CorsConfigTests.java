package com.jasonhatfield.homeschoolinteractive.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CorsConfigTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCorsAllowedOrigin() throws Exception {
        mockMvc.perform(get("/assignments") // Example endpoint
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk()); // Expecting OK status for allowed origin
    }

    @Test
    void testCorsDisallowedOrigin() throws Exception {
        mockMvc.perform(get("/assignments") // Example endpoint
                .header("Origin", "http://localhost:8080"))
                .andExpect(status().isForbidden()); // Expecting Forbidden status for disallowed origin
    }
}
