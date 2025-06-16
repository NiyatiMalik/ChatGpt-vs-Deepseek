package com.chatgpt.Service;

import com.chatgpt.Model.Question;
import com.chatgpt.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByTopic(String topic) {
        return questionRepository.findByTopic(topic);
    }

    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    public List<Question> getQuestionsByTopicAndDifficulty(String topic, String difficulty) {
        return questionRepository.findByTopicAndDifficulty(topic, difficulty);
    }

    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }
}
