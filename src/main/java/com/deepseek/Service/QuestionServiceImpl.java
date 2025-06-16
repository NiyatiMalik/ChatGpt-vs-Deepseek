package com.deepseek.Service;

import com.deepseek.Model.Question;
import com.deepseek.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(String id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    @Override
    public List<Question> getQuestionsByTopic(String topic) {
        return questionRepository.findByTopic(topic);
    }

    @Override
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    @Override
    public List<Question> getQuestionsByTopicAndDifficulty(String topic, String difficulty) {
        return questionRepository.findByTopicAndDifficulty(topic, difficulty);
    }

    @Override
    public Question updateQuestion(String id, Question question) {
        question.setId(id);
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }

}
