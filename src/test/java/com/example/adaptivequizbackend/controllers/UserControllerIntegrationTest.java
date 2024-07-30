package com.example.adaptivequizbackend.controllers;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired

    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setId("testId");
        testUser.setUsername("test");
        testUser.setPassword("passwordTest");
        testUser.setEmail("testemail@test.com");
        testUser.setEasyScore(0);
        testUser.setMediumScore(0);
        testUser.setHardScore(0);

        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        when(userRepository.findUserByUsername("test")).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));
    }

    // Passed
    @Test
    public void createUser() throws Exception {
        String user = "{\"username\":\"newuser\",\"password\":\"newpassword\",\"email\":\"newuser@newcastle.com\"}";

        mockMvc.perform(post("/api/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"));
    }




    // Passed
    @Test
    public void getUserById() throws Exception {
        mockMvc.perform(get("/api/users/testId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"));
    }

    // Passed
    @Test
    public void getUserByUsername() throws Exception {
        mockMvc.perform(get("/api/users/username/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testemail@test.com"));
    }

    // Passed
    @Test
    public void loginCheck() throws Exception {
        User loginRequest = new User();
        loginRequest.setUsername("test");
        loginRequest.setPassword("passwordTest");

        mockMvc.perform(post("/api/users/loginCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // Passed
    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}