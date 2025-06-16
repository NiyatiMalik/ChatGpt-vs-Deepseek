package com.chatgpt.Model;

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
    private String topic;
    private String difficulty; // Easy / Medium / Hard
    private String problemStatement;
    private String sampleInput;
    private String sampleOutput;
    private String constraints;
    private List<String> hints; // 2-3 hints
    private String solution; // full solution

    // Constructors
    public Question() {}

}
