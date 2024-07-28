package com.example.adaptivequizbackend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "questions")
public class Question {
    @Id
    private String questionId;

    private String difficulty;

    private String questionText;

    private String questionType;

    private List<String> choices;

    private String correctAnswer;

    private String feedback;

    public Question(String questionId, String difficulty, String questionText, String questionType, List<String> choices, String correctAnswer, String feedback) {
        this.questionId = questionId;
        this.difficulty = difficulty;
        this.questionText = questionText;
        this.questionType = questionType;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.feedback = feedback;
    }

    public boolean isCorrect(String userAnswer) {
        switch (this.questionType.toLowerCase()) {
            case "multiple choice":
                return this.correctAnswer.equalsIgnoreCase(userAnswer);
            case "true/false":
                return this.correctAnswer.equalsIgnoreCase(userAnswer);
            case "short answer":
                return this.correctAnswer.trim().equalsIgnoreCase(userAnswer.trim());
            default:
                return false;
        }
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
