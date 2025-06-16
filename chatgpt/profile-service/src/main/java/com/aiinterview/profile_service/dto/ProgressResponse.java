package com.aiinterview.profile_service.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ProgressResponse {
    private int totalSolved;
    private Map<String, QuestionInfo> questions;

    public ProgressResponse() {} // No-args constructor

    public ProgressResponse(int totalSolved, Map<String, QuestionInfo> questions) {
        this.totalSolved = totalSolved;
        this.questions = questions;
    }

    @Data
    public static class QuestionInfo {
        private String name;
        private String difficulty;

        public QuestionInfo() {} // No-args constructor

        public QuestionInfo(String name, String difficulty) {
            this.name = name;
            this.difficulty = difficulty;
        }
    }
}
