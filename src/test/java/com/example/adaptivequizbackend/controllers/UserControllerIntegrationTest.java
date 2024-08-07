package com.example.adaptivequizbackend.controllers;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        User testUser = new User("testId", "test", "passwordTest", "testemail@test.com", 0, 0, 0);
        userRepository.save(testUser);
    }

    @Test
    public void createUser() throws Exception {
        String userJson = "{\"username\":\"newuser\",\"password\":\"newpassword\",\"email\":\"newuser@newcastle.com\"}";

        mockMvc.perform(post("/api/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@newcastle.com"));
    }

    @Test
    public void getUserById() throws Exception {
        mockMvc.perform(get("/api/users/testId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"));
    }

    @Test
    public void getUserByUsername() throws Exception {
        mockMvc.perform(get("/api/users/username/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testemail@test.com"));
    }

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

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("test"));
    }
}
