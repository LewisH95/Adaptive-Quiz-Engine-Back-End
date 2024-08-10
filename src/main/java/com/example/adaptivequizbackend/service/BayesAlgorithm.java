package com.example.adaptivequizbackend.service;

import org.springframework.stereotype.Service;

@Service
public class BayesAlgorithm {

    private static final int CORRECT_STREAK_LIMIT = 5;
    private static final int INCORRECT_STREAK_LIMIT = 5;
    private int correctInARow = 0;
    private int incorrectInARow = 0;
    private String currentDifficulty = "easy";

    /**
     * Updates the current difficulty level based on the user's performance.
     * If the user answers a certain number of questions correctly or incorrectly in a row, the difficulty is adjusted.
     * This is designed and implemented considering Bloom's Mastery Learning (1968)
     * @param correct Indicates whether the user's answer was correct or not.
     * @return The updated difficulty level.
     */
    public String updateDifficulty(boolean correct) {
        if (correct) {
            correctInARow++;
            incorrectInARow = 0;
        } else {
            incorrectInARow++;
            correctInARow = 0;
        }

        // Increases the difficulty if correct streak limit is reached
        if (correctInARow >= CORRECT_STREAK_LIMIT) {
            if (currentDifficulty.equals("easy")) {
                currentDifficulty = "medium";
            } else if (currentDifficulty.equals("medium")) {
                currentDifficulty = "hard";
            }

            correctInARow = 0;
            // Decrease difficulty if incorrect streak limit is reached
        } else if (incorrectInARow >= INCORRECT_STREAK_LIMIT) {
            if (currentDifficulty.equals("hard")) {
                currentDifficulty = "medium";
            } else if (currentDifficulty.equals("medium")) {
                currentDifficulty = "easy";
            }

            incorrectInARow = 0;
        }

        return currentDifficulty;
    }

    /**
     * Resets the user's correct and incorrect streaks and sets the difficulty back to easy.
     */
    public void resetStreak() {
        correctInARow = 0;
        incorrectInARow = 0;
        currentDifficulty = "easy";
    }
}
