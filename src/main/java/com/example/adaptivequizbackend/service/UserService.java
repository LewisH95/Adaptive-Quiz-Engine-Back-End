package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean loginAuthentication(String username, String password) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }


    public void updateProgress(String username, String difficulty, boolean correct) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            switch (difficulty.toLowerCase()) {
                case "easy":
                    user.setEasyScore(user.getEasyScore() + (correct ? 1 : 0));
                    break;
                case "medium":
                    user.setMediumScore(user.getMediumScore() + (correct ? 1 : 0));
                    break;
                case "hard":
                    user.setHardScore(user.getHardScore() + (correct ? 1 : 0));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid difficulty level");
            }
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }
    
    public Map<String, Integer> getScores(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getScores();
        }
        return new HashMap<>();
    }


}
