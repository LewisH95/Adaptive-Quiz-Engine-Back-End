package com.example.adaptivequizbackend.api.controller;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    // Test Message for the API connecting to the front end
    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello World from Spring";
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/loginCheck")
    public boolean loginCheck(@RequestBody User loginRequest) {
        return userService.loginAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/updateProgress")
    public void updateProgress(@RequestParam String userId, @RequestParam String difficulty, @RequestParam boolean correct) {
        userService.updateProgress(userId, difficulty, correct);
    }

    @PutMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
