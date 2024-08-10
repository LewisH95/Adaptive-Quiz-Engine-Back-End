package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.api.model.Question;
import com.example.adaptivequizbackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * QuestionService handles the business logic related to quiz questions within the adaptive quiz system. This service
 * manages operations such as retrieving questions based on difficulty, providing feedback on answers, and updating
 * user progress. It interacts with the QuestionRepository to access the question collection in the MongoDB Atlas database
 * and the UserService for updating user performance metrics.
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final UserService userService;

    public QuestionService(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }


    public Question getQuestionByDifficulty(String difficulty) {
        List<Question> questions = questionRepository.findByDifficulty(difficulty.toLowerCase());
        return getRandomQuestion(questions);
    }


    private Question getRandomQuestion(List<Question> questions) {
        if (questions.isEmpty()) {
            throw new NoSuchElementException("No questions available");
        }
        int index = (int) (Math.random() * questions.size());
        return questions.get(index);
    }


    public Question getQuestionById(String id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    public String getFeedback(String questionId, String userAnswer) {
        Question question = getQuestionById(questionId);
        if (question != null) {
            if (!question.getCorrectAnswer().equals(userAnswer)) {
                return "Incorrect: " + question.getQuestionText() + " " + question.getFeedback();
            } else {
                return "Correct: " + question.getQuestionText() + " Your Answer: " + userAnswer;
            }
        }
        return "Question not found";
    }

    public void updateProgress(String userId, String difficulty, boolean correct) {
        userService.updateProgress(userId, difficulty, correct);
    }

    public Map<String, Integer> getStudentProgress(String userId) {
        return userService.getScores(userId);
    }

}
