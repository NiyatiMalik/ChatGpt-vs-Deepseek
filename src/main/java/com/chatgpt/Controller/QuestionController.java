package com.chatgpt.Controller;

import com.chatgpt.Model.Question;
import com.chatgpt.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Optional<Question> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/filter")
    public List<Question> getQuestionsByFilter(@RequestParam(required = false) String topic,
                                               @RequestParam(required = false) String difficulty) {
        if (topic != null && difficulty != null) {
            return questionService.getQuestionsByTopicAndDifficulty(topic, difficulty);
        } else if (topic != null) {
            return questionService.getQuestionsByTopic(topic);
        } else if (difficulty != null) {
            return questionService.getQuestionsByDifficulty(difficulty);
        } else {
            return questionService.getAllQuestions();
        }
    }

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
    }
}
