package com.example.adaptivequizbackend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;

    private String password;

    private String email;

    private int easyScore = 0;
    private int mediumScore = 0;
    private int hardScore = 0;

    public User() {}

    public User(String id, String username, String password, String email, int easyScore, int mediumScore, int hardScore) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.easyScore = easyScore;
        this.mediumScore = mediumScore;
        this.hardScore = hardScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEasyScore() {
        return easyScore;
    }

    public void setEasyScore(int easyScore) {
        this.easyScore = easyScore;
    }

    public int getMediumScore() {
        return mediumScore;
    }

    public void setMediumScore(int mediumScore) {
        this.mediumScore = mediumScore;
    }

    public int getHardScore() {
        return hardScore;
    }

    public void setHardScore(int hardScore) {
        this.hardScore = hardScore;
    }

    public Map<String, Integer> getScores() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("easy", easyScore);
        scores.put("medium", mediumScore);
        scores.put("hard", hardScore);
        return scores;
    }
}
