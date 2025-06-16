package com.aiinterview.profile_service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_submission_deepseek",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"userId", "questionId"}
        )
)
public class UserSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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



    public String getSubmittedCode() {
        return submittedCode;
    }

    public void setSubmittedCode(String submittedCode) {
        this.submittedCode = submittedCode;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Column(nullable = false)
    @JsonProperty("isCorrect") // Explicit JSON mapping
    private boolean correct;

    // Use standard getter/setter names
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }


    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String submittedCode;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
}
