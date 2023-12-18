package com.jasonhatfield.homeschoolinteractive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasonhatfield.homeschoolinteractive.model.User;
import com.jasonhatfield.homeschoolinteractive.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        User newUser = new User("newUser", "password123", "STUDENT");
        given(userService.registerUser(any(User.class))).willReturn(newUser);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newUser"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String username = "existingUser";
        given(userService.existsByUsername(username)).willReturn(true);

        mockMvc.perform(delete("/users/delete/" + username))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));

        verify(userService).deleteUserByUsername(username);
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"})
    public void testDeleteUserAsTeacher() throws Exception {
        String username = "existingUser";
        given(userService.existsByUsername(username)).willReturn(true);

        mockMvc.perform(delete("/users/delete/" + username))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));

        verify(userService).deleteUserByUsername(username);
    }

}
