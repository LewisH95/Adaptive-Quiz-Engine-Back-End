package com.example.adaptivequizbackend.repository;

import com.example.adaptivequizbackend.api.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
/**
 * QuestionRepository is a Spring Data MongoDB repository interface for managing question entities.
 * This repository provides methods for performing CRUD operations and custom queries on the questions collection
 * in the MongoDB Atlas database.
 */
public interface QuestionRepository extends MongoRepository<Question, String>{
    List<Question> findByDifficulty(String difficulty);

    Question findByQuestionId(String questionId);


}
