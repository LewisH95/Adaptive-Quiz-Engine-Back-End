package com.example.adaptivequizbackend.repository;

import com.example.adaptivequizbackend.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
/**
 * UserRepository is a Spring Data MongoDB repository interface for managing User entities.
 * This repository provides methods for performing CRUD operations and custom queries on the users collection
 * in the MongoDB Atlas database.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String iD);

    Optional<User> findUserByUsername(String username);
}
