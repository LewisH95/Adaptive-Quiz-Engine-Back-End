package com.example.adaptivequizbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class AdaptiveQuizBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdaptiveQuizBackEndApplication.class, args);
    }

}
