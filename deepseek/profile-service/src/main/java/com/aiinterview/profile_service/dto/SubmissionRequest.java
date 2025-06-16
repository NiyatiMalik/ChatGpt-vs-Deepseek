package com.aiinterview.profile_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class SubmissionRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String questionId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("isCorrect") // Explicit mapping
    private boolean correct;

    // Getters/setters use "correct"
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }



    public String getSubmittedCode() {
        return submittedCode;
    }

    public void setSubmittedCode(String submittedCode) {
        this.submittedCode = submittedCode;
    }

    @NotBlank
    private String submittedCode;

    // Getters and setters
}