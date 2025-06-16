package com.cs540.code_service.model;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionResult {
    private List<TestCaseResult> testCaseResults;
    private int passedCount;
    private int totalCount;
    private double score;
    private String overallStatus;
    private boolean allPassed;  // New flag
}