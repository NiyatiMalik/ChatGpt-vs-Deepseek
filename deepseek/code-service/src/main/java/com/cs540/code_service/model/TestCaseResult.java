package com.cs540.code_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCaseResult {
    private String input;
    private String expectedOutput;
    private String actualOutput;
    private String status; // "PASSED", "FAILED", "TIMEOUT", "ERROR"
    private long executionTime;
    private long memoryUsage;
    private String error;
    private boolean hidden;
}