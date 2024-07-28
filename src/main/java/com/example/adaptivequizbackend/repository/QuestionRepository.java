package com.example.adaptivequizbackend.repository;

import com.example.adaptivequizbackend.api.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
public interface QuestionRepository extends MongoRepository<Question, String>{
    List<Question> findByDifficulty(String difficulty);
}
