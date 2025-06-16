package com.cs540.code_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "submissions")
@Data
public class Submission {
    @Id
    private String id;
    private String userId;
    private String problemId;
    private String code;
    private String language;
    private Date timestamp;
    private double score;
    private String status;
    private List<TestCaseResult> executionResults;
}