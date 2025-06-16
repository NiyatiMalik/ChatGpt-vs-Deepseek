package com.aiinterview.profile_service.dto;

import lombok.Data;

@Data
public class SubmissionRequest {
    private Long userId;
    private String questionId;
    private boolean correct;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

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

    private String submittedCode;


}
