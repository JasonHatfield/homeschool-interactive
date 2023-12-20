package com.jasonhatfield.homeschoolinteractive.security;

// import com.jasonhatfield.homeschoolinteractive.model.User;
import com.jasonhatfield.homeschoolinteractive.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityTests.class);

    @BeforeEach
    void setUp() {
        logger.info("Setting up tests");
        // Ensure 'existingUser' and 'newUser' are not present before tests
        Arrays.asList("existingUser", "newUser").forEach(username -> {
            if (userService.existsByUsername(username)) {
                userService.deleteUserByUsername(username);
            }
        });

        // Create 'existingUser' for testing
        // User existingUser = new User("existingUser", "password", "TEACHER");
        // userService.registerUser(existingUser);
    }

    @AfterEach
    void tearDown() {
        logger.info("Cleaning up after tests");
        // Delete 'existingUser' and 'newUser' after tests
        Arrays.asList("existingUser", "newUser").forEach(userService::deleteUserByUsername);
    }

    @Test
    public void accessProtectedEndpointWithoutAuthenticationShouldBeDenied() throws Exception {
        logger.info("Testing access to a protected endpoint without authentication");
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    // @WithMockUser(username = "teacher", roles = {"TEACHER"})
    public void accessRoleSpecificEndpointWithCorrectRoleShouldBeAllowed() throws Exception {
        logger.info("Testing access to a role-specific endpoint with the correct role");
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    // @WithMockUser(username = "student", roles = {"STUDENT"})
    public void accessRoleSpecificEndpointWithIncorrectRoleShouldBeDenied() throws Exception {
        logger.info("Testing access to a role-specific endpoint with an incorrect role");
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void registerUserWithExistingUsernameShouldFail() throws Exception {
        logger.info("Testing registration with an existing username");
        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content("{\"username\": \"existingUser\", \"password\": \"pass\", \"role\": \"STUDENT\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserWithValidDataShouldSucceed() throws Exception {
        logger.info("Testing registration with valid data");
        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content("{\"username\": \"newUser\", \"password\": \"pass\", \"role\": \"STUDENT\"}"))
                .andExpect(status().isCreated());
    }

}
