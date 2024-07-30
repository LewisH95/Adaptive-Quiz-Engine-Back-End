package com.example.adaptivequizbackend.controllers;

import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.repository.QuestionRepository;
import com.example.adaptivequizbackend.service.BayesAlgorithm;
import com.example.adaptivequizbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @MockBean
    private BayesAlgorithm bayesAlgorithm;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        questionRepository.save(new Question("E1", "easy", "What is 2 + 2?", "multiple choice",
                Arrays.asList("3", "4", "5", "6"), "4", "2 plus 2 equals 4."));

        when(bayesAlgorithm.updateDifficulty(anyBoolean())).thenReturn("easy");
        when(userService.getScores(anyString())).thenReturn(new HashMap<>());
    }

    @Test
    public void getQuestionByDifficulty() throws Exception {
        mockMvc.perform(get("/api/getQuestion")
                        .param("difficulty", "easy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").isNotEmpty())
                .andExpect(jsonPath("$.difficulty").value("easy"))
                .andExpect(jsonPath("$.questionId").isNotEmpty())
                .andExpect(jsonPath("$.questionType").isNotEmpty())
                .andExpect(jsonPath("$.choices").isArray())
                .andExpect(jsonPath("$.correctAnswer").isNotEmpty())
                .andExpect(jsonPath("$.feedback").isNotEmpty());
    }


    @Test
    public void updateDifficulty() throws Exception {
        mockMvc.perform(get("/api/updateDifficulty")
                        .param("correct", "true")
                        .param("currentDifficulty", "easy"))
                .andExpect(status().isOk())
                .andExpect(content().string("easy"));
    }

    @Test
    public void updateProgress() throws Exception {
        mockMvc.perform(post("/api/updateProgress")
                        .param("userId", "testUser")
                        .param("correct", "true")
                        .param("difficulty", "easy"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFeedback() throws Exception {
        mockMvc.perform(get("/api/feedback")
                        .param("questionId", "E1")
                        .param("userAnswer", "4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Correct: What is 2 + 2? Your Answer: 4"));
    }

    @Test
    public void getStudentProgress() throws Exception {
        Map<String, Integer> progress = new HashMap<>();
        progress.put("easy", 10);
        when(userService.getScores("testUser")).thenReturn(progress);

        mockMvc.perform(get("/api/progress")
                        .param("userId", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.easy").value(10))
                .andExpect(jsonPath("$.medium").doesNotExist())
                .andExpect(jsonPath("$.hard").doesNotExist());
    }

    @Test
    public void resetStreaks() throws Exception {
        mockMvc.perform(post("/api/resetStreaks"))
                .andExpect(status().isOk());
    }
}
