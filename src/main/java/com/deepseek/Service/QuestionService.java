package com.deepseek.Service;

import com.deepseek.Model.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Question getQuestionById(String id);
    List<Question> getQuestionsByTopic(String topic);
    List<Question> getQuestionsByDifficulty(String difficulty);
    List<Question> getQuestionsByTopicAndDifficulty(String topic, String difficulty);
    Question updateQuestion(String id, Question question);
    void deleteQuestion(String id);
}
