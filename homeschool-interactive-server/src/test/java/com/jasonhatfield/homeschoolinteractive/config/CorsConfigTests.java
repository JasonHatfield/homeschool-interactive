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

/**
 * This class contains test cases for the CORS (Cross-Origin Resource Sharing) configuration.
 * It ensures that the CORS configuration allows or disallows specific origins.
 */
@SpringBootTest
public class CorsConfigTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test case to verify that the CORS configuration allows a specific origin.
     * It sends a GET request to the "/assignments" endpoint with a specific origin header,
     * and expects an OK status code (200) indicating that the origin is allowed.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCorsAllowedOrigin() throws Exception {
        mockMvc.perform(get("/assignments") // Example endpoint
                .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk()); // Expecting OK status for allowed origin
    }

    /**
     * Test case to verify that the CORS configuration disallows a specific origin.
     * It sends a GET request to the "/assignments" endpoint with a specific origin header,
     * and expects a Forbidden status code (403) indicating that the origin is disallowed.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCorsDisallowedOrigin() throws Exception {
        mockMvc.perform(get("/assignments") // Example endpoint
                .header("Origin", "http://localhost:8080"))
                .andExpect(status().isForbidden()); // Expecting Forbidden status for disallowed origin
    }
}
