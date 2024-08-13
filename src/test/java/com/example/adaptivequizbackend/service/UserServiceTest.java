package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the UserService.
 * This class verifies the functionality of the UserService, ensuring that
 * user-related operations like updating progress, retrieving scores, and
 * authenticating logins work as expected. The tests must be run in isolation
 * to avoid interference between test cases.
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final String testUsername = "test";
    private final String testPassword = "password";

    // TESTS MUST BE RAN IN ISOLATION

    // Passed
    @BeforeEach
    public void createUser() {
        userRepository.findUserByUsername(testUsername).ifPresent(userRepository::delete);


        User user = new User();
        user.setUsername(testUsername);
        user.setPassword(testPassword);
        user.setEasyScore(0);
        user.setMediumScore(0);
        user.setHardScore(0);
        userRepository.save(user);
    }

    // Passed
    @Test
    public void updateProgressEasy() {
        userService.updateProgress(testUsername, "easy", true);


        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();


        assertEquals(1, user.getEasyScore(), "Progress for 'easy' should be 1 after update");
    }

    // Passed
    @Test
    public void updateProgressMedium() {

        userService.updateProgress(testUsername, "medium", true);


        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();


        assertEquals(1, user.getMediumScore(), "Progress for 'medium' should be 1 after update");
    }

    // Passed
    @Test
    public void updateProgressHard() {
        userService.updateProgress(testUsername, "hard", true);

        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();

        assertEquals(1, user.getHardScore(), "Progress for 'hard' should be 1 after update");
    }

    // Passed (Fails if tests above are run at same time. As it will change the scores).
    @Test
    public void getScores() {
        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present");
        User user = optionalUser.get();

        assertEquals(0, user.getEasyScore(), "Easy score should be 0");
        assertEquals(0, user.getMediumScore(), "Medium score should be 0");
        assertEquals(0, user.getHardScore(), "Hard score should be 0");
    }

    // Passed
    @Test
    public void loginAuthenticationSuccess() {

        boolean result = userService.loginAuthentication(testUsername, testPassword);
        assertTrue(result, "Login should be successful when given the correct username and password");
    }

    // Passed
    @Test
    public void loginAuthenticationFail() {
        boolean result = userService.loginAuthentication(testUsername, "wrongPassword");
        assertFalse(result, "Login should fail when given the incorrect password");
    }
}
