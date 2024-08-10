package com.example.adaptivequizbackend.api.controller;

import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.service.BayesAlgorithm;
import com.example.adaptivequizbackend.service.QuestionService;
import com.example.adaptivequizbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * QuestionController handles API requests related to the adaptive quiz system. This controller
 * manages quiz question retrieval, difficulty adjustment using  BayesAlgorithm service
 * , quiz progress updates, and feedback delivery. It acts as a bridge
 * between the front-end and the services that process quiz-related logic.
 */
@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    private final BayesAlgorithm bayesAlgorithm;

    private final UserService userService;

    private final Map<String, Integer> quizProgress = new HashMap<>();

    public QuestionController(QuestionService questionService, BayesAlgorithm bayesAlgorithm, UserService userService) {
        this.questionService = questionService;
        this.bayesAlgorithm = bayesAlgorithm;
        this.userService = userService;
    }

    @GetMapping("/getQuestion")
    public Question getQuestion(@RequestParam(value = "difficulty", required = false, defaultValue = "Easy") String difficulty) {
        return questionService.getQuestionByDifficulty(difficulty);
    }

    @GetMapping("/updateDifficulty")
    public String updateDifficulty(@RequestParam("correct") boolean correct, @RequestParam("currentDifficulty") String currentDifficulty) {
        return bayesAlgorithm.updateDifficulty(correct);
    }


    @PostMapping("/updateProgress")
    public void updateProgress(@RequestParam String userId, @RequestParam boolean correct, @RequestParam String difficulty) {
        if (correct) {
            questionService.updateProgress(userId, difficulty, correct);
        }
    }

    @GetMapping("/feedback")
    public String getFeedback(@RequestParam("questionId") String questionId, @RequestParam("userAnswer") String userAnswer) {
        return questionService.getFeedback(questionId, userAnswer);
    }

    @GetMapping("/progress")
    public Map<String, Integer> getStudentProgress(@RequestParam String userId) {
        return questionService.getStudentProgress(userId);
    }

    @PostMapping("/resetStreaks")
    public ResponseEntity<Void> resetStreaks() {
        bayesAlgorithm.resetStreak();
        return ResponseEntity.ok().build();
    }
}
