package com.deepseek.Repository;

import com.deepseek.Model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByTopic(String topic);
    List<Question> findByDifficulty(String difficulty);
    List<Question> findByTopicAndDifficulty(String topic, String difficulty);
}
