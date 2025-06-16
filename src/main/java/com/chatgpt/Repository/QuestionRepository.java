package com.chatgpt.Repository;

import com.chatgpt.Model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByTopic(String topic);
    List<Question> findByDifficulty(String difficulty);
    List<Question> findByTopicAndDifficulty(String topic, String difficulty);
}
