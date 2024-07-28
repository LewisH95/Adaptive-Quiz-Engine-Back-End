package com.example.adaptivequizbackend.service;

import org.springframework.stereotype.Service;

@Service
public class BayesAlgorithm {

    private static final int CORRECT_STREAK_LIMIT = 5;
    private static final int INCORRECT_STREAK_LIMIT = 5;
    private int correctInARow = 0;
    private int incorrectInARow = 0;
    private String currentDifficulty = "easy";

    public String updateDifficulty(boolean correct) {
        if (correct) {
            correctInARow++;
            incorrectInARow = 0;
        } else {
            incorrectInARow++;
            correctInARow = 0;
        }


        if (correctInARow >= CORRECT_STREAK_LIMIT) {
            if (currentDifficulty.equals("easy")) {
                currentDifficulty = "medium";
            } else if (currentDifficulty.equals("medium")) {
                currentDifficulty = "hard";
            }

            correctInARow = 0;
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
}
