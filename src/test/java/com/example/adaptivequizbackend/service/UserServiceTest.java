package com.example.adaptivequizbackend.service;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final String testUsername = "test";
    private final String testPassword = "password";

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
    public void testUpdateProgressEasy() {
        userService.updateProgress(testUsername, "easy", true);


        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();


        assertEquals(1, user.getEasyScore(), "Progress for 'easy' should be 1 after update");
    }

    // Passed
    @Test
    public void testUpdateProgressMedium() {

        userService.updateProgress(testUsername, "medium", true);


        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();


        assertEquals(1, user.getMediumScore(), "Progress for 'medium' should be 1 after update");
    }

    // Passed
    @Test
    public void testUpdateProgressHard() {
        userService.updateProgress(testUsername, "hard", true);

        Optional<User> optionalUser = userRepository.findUserByUsername(testUsername);
        assertTrue(optionalUser.isPresent(), "User should be present after update");
        User user = optionalUser.get();

        assertEquals(1, user.getHardScore(), "Progress for 'hard' should be 1 after update");
    }

    // Passed (Fails if tests above are run at same time. As it will change the scores).
    @Test
    public void testGetScores() {
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
