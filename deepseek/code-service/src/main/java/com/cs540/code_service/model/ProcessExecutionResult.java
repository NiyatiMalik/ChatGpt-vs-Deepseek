package com.cs540.code_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessExecutionResult {
    private boolean timeout;
    private int exitCode;
    private String output;
    private String error;
    private long executionTime;
    private long memoryUsage;

    // Add this convenience constructor
    public ProcessExecutionResult(boolean timeout, int exitCode, String output, String error) {
        this(timeout, exitCode, output, error, 0, 0);
    }
}