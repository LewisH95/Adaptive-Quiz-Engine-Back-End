package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.AdaptiveQuizBackEndApplication;
import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AdaptiveQuizBackEndApplication.class)
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testGetQuestionByDifficultyEasy() {

        List<Question> expectedQuestions = questionRepository.findByDifficulty("easy");


        assertFalse(expectedQuestions.isEmpty(), "No easy questions found in the database.");


        Question question = questionService.getQuestionByDifficulty("easy");


        assertNotNull(question, "Question should not be null");
        assertEquals("easy", question.getDifficulty(), "Difficulty should be 'easy'");


        Optional<Question> matchingQuestion = expectedQuestions.stream()
                .filter(q -> q.getQuestionText().equals(question.getQuestionText()))
                .findFirst();

        assertTrue(matchingQuestion.isPresent(), "The retrieved question should be among the expected questions.");
    }

    @Test
    public void testGetQuestionByDifficultyMedium() {

        List<Question> expectedQuestions = questionRepository.findByDifficulty("medium");


        assertFalse(expectedQuestions.isEmpty(), "No medium questions found in the database.");


        Question question = questionService.getQuestionByDifficulty("medium");


        assertNotNull(question, "Question should not be null");
        assertEquals("medium", question.getDifficulty(), "Difficulty should be 'medium'");


        Optional<Question> matchingQuestion = expectedQuestions.stream()
                .filter(q -> q.getQuestionText().equals(question.getQuestionText()))
                .findFirst();

        assertTrue(matchingQuestion.isPresent(), "The retrieved question should be among the expected questions.");
    }

    @Test
    public void testGetQuestionByDifficultyHard() {

        List<Question> expectedQuestions = questionRepository.findByDifficulty("hard");


        assertFalse(expectedQuestions.isEmpty(), "No hard questions found in the database.");


        Question question = questionService.getQuestionByDifficulty("hard");


        assertNotNull(question, "Question should not be null");
        assertEquals("hard", question.getDifficulty(), "Difficulty should be 'hard'");


        Optional<Question> matchingQuestion = expectedQuestions.stream()
                .filter(q -> q.getQuestionText().equals(question.getQuestionText()))
                .findFirst();

        assertTrue(matchingQuestion.isPresent(), "The retrieved question should be among the expected questions.");
    }

}
