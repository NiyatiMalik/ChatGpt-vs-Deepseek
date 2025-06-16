package com.deepseek.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@Document(collection = "Questions")
public class Question {
    @Id
    private String id;
    private String title;
    private String problemStatement;
    private List<String> sampleInput;
    private List<String> sampleOutput;
    private List<String> constraints;
    private String topic;
    private String difficulty; // EASY, MEDIUM, HARD
    private List<Hint> hints;
    private Solution solution;

    // constructors, getters, setters
    public Question() {
    }

    public Question(String title, String problemStatement, List<String> sampleInput,
                    List<String> sampleOutput, List<String> constraints, String topic,
                    String difficulty, List<Hint> hints, Solution solution) {
        this.title = title;
        this.problemStatement = problemStatement;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
        this.constraints = constraints;
        this.topic = topic;
        this.difficulty = difficulty;
        this.hints = hints;
        this.solution = solution;
    }

    public Question(String number, String q1, String content1, Object o, Object o1, Object o2, String arrays, String easy, Object o3, Object o4) {
    }
}
