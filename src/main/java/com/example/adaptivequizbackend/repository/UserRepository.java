package com.example.adaptivequizbackend.repository;

import com.example.adaptivequizbackend.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String iD);

    Optional<User> findUserByUsername(String username);
}
