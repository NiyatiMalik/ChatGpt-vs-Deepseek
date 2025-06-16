package com.cs540.code_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testcases")
public class TestCase {

    @Id
    private String id;
    private String problemId;
    private Object input;             // 👈 Changed from String to Object
    private Object expectedOutput;    // 👈 Changed from String to Object
    private int timeoutSeconds;
    private boolean hidden;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public Object getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(Object expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}