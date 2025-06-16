package com.cs540.code_service.model;

import lombok.Data;

@Data
public class ExecutionRequest {
    private String code;
    private String language;
    private String input;  // Keep as String since we convert before execution
    private String userId;
    private String problemId;
    private int timeoutSeconds = 5;
}