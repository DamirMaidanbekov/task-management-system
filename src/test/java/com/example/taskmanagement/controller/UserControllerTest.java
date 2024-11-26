package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.dto.UserDTO;
import com.example.taskmanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegister() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");

        UserDTO registeredUser = new UserDTO();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123");

        when(userService.register(userDTO)).thenReturn(registeredUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password123";

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);

        when(userService.login(email, password)).thenReturn(Optional.of(userDTO));

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testLoginFailure() throws Exception {
        String email = "test@example.com";
        String password = "wrongpassword";

        when(userService.login(email, password)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isUnauthorized());
    }
}
