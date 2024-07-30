package com.example.adaptivequizbackend.api.controller;

import com.example.adaptivequizbackend.api.model.User;
import com.example.adaptivequizbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Logic to create the user
        return new ResponseEntity<>(user, HttpStatus.OK);
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

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
