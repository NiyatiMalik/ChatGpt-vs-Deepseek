package com.deepseek.Controller;

import com.deepseek.Model.Question;
import com.deepseek.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable String id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/topic/{topic}")
    public List<Question> getQuestionsByTopic(@PathVariable String topic) {
        return questionService.getQuestionsByTopic(topic);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<Question> getQuestionsByDifficulty(@PathVariable String difficulty) {
        return questionService.getQuestionsByDifficulty(difficulty);
    }

    @GetMapping("/filter")
    public List<Question> getQuestionsByTopicAndDifficulty(
            @RequestParam String topic,
            @RequestParam String difficulty) {
        return questionService.getQuestionsByTopicAndDifficulty(topic, difficulty);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable String id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
    }
}
