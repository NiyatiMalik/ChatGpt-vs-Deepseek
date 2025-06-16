package com.deepseek.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.codingplatform.questionservice.repository")
@EnableMongoAuditing
public class MongoConfig {
}
