package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.AdaptiveQuizBackEndApplication;
import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Unit tests for the QuestionService.
 * This class verifies the functionality of the QuestionService, ensuring that
 * questions are correctly retrieved based on their difficulty level.
 * The tests check for both non-null responses and correct difficulty values
 * in the retrieved questions.
 */
@SpringBootTest(classes = AdaptiveQuizBackEndApplication.class)
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    // Passed
    @Test
    public void getQuestionByDifficultyEasyNotNull() {
        Question question = questionService.getQuestionByDifficulty("easy");
        assertNotNull(question, "Question should not be null for difficulty 'easy'");
    }

    // Passed
    @Test
    public void getQuestionByDifficultyEasy() {
        Question question = questionService.getQuestionByDifficulty("easy");
        assertEquals("easy", question.getDifficulty(), "Difficulty should be 'easy' for the retrieved question");
    }

    // Passed
    @Test
    public void getQuestionByDifficultyMediumNotNull() {
        Question question = questionService.getQuestionByDifficulty("medium");
        assertNotNull(question, "Question should not be null for difficulty 'medium'");
    }

    // Passed
    @Test
    public void getQuestionByDifficultyMedium() {
        Question question = questionService.getQuestionByDifficulty("medium");
        assertEquals("medium", question.getDifficulty(), "Difficulty should be 'medium' for the retrieved question");
    }

    // Passed
    @Test
    public void getQuestionByDifficultyHardNotNull() {
        Question question = questionService.getQuestionByDifficulty("hard");
        assertNotNull(question, "Question should not be null for difficulty 'hard'");
    }

    // Passed
    @Test
    public void getQuestionByDifficultyHard() {
        Question question = questionService.getQuestionByDifficulty("hard");
        assertEquals("hard", question.getDifficulty(), "Difficulty should be 'hard' for the retrieved question");
    }
}
