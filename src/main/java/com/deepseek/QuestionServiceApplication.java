package com.deepseek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.deepseek.Repository")  // Point to your exact repo package
public class QuestionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionServiceApplication.class, args);
    }
}
