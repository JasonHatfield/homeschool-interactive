package com.jasonhatfield.homeschoolinteractive.controller;

import com.jasonhatfield.homeschoolinteractive.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void deleteUserTest_Success() throws Exception {
        String username = "existingUser";
        when(userService.existsByUsername(username)).thenReturn(true);
        doNothing().when(userService).deleteUserByUsername(username);

        mockMvc.perform(delete("/users/delete/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));
        verify(userService).deleteUserByUsername(username);
    }

    @Test
    void deleteUserTest_NotFound() throws Exception {
        String username = "nonExistingUser";
        when(userService.existsByUsername(username)).thenReturn(false);

        mockMvc.perform(delete("/users/delete/{username}", username))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Username not found!"));
        verify(userService, never()).deleteUserByUsername(username);
    }

    @Test
    void deleteUserTest_InvalidUsername() throws Exception {
        String invalidUsername = "invalidUser";
        when(userService.existsByUsername(invalidUsername)).thenReturn(false);

        mockMvc.perform(delete("/users/delete/{username}", invalidUsername))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Username not found!"));
        verify(userService, never()).deleteUserByUsername(invalidUsername);
    }

}
