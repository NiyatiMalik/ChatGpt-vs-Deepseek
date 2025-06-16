package com.cs540.code_service.model;

import lombok.Data;

@Data
public class ExecutionResult {
    private String output;
    private String error;
    private long executionTime;
    private long memoryUsage;
    private String status; // "SUCCESS", "COMPILATION_ERROR", "RUNTIME_ERROR", "TIMEOUT"
}
