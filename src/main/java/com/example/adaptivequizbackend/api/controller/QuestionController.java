package com.example.adaptivequizbackend.api.controller;

import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.service.BayesAlgorithm;
import com.example.adaptivequizbackend.service.QuestionService;
import com.example.adaptivequizbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @GetMapping("/questionTest")
    public Question getQuestionTest(@RequestParam(value = "difficulty", required = false, defaultValue = "Easy") String difficulty) {
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

    @GetMapping("/feedback/multiple")
    public Map<String, String> getMultipleFeedback(
            @RequestParam List<String> questionIds,
            @RequestParam List<String> userAnswers) {

        if (questionIds.size() != userAnswers.size()) {
            throw new IllegalArgumentException("Mismatch between question IDs and user answers.");
        }

        Map<String, String> feedbackMap = new HashMap<>();
        for (int i = 0; i < questionIds.size(); i++) {
            String questionId = questionIds.get(i);
            String userAnswer = userAnswers.get(i);

            Question question = questionService.getQuestionById(questionId);
            if (question.isCorrect(userAnswer)) {
               getFeedback(questionId, userAnswer);
                if (!question.isCorrect(userAnswer)) {
                    feedbackMap.put(questionId, question.getFeedback());
                } else {
                    feedbackMap.put(questionId, "Correct answer.");
                }
            } else {
                feedbackMap.put(questionId, "Question not found.");
            }
        }
        return feedbackMap;
    }

    @GetMapping("/progress")
    public Map<String, Integer> getStudentProgress(@RequestParam String userId) {
        return questionService.getStudentProgress(userId);
    }
}
