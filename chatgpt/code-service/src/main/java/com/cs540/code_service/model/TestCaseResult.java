package com.cs540.code_service.model;

public class TestCaseResult {

    private Object input;
    private Object expectedOutput;
    private Object actualOutput;  // 👈 Changed to Object
    private boolean passed;
    private long executionTimeMs;
    private String errorMessage;

    // Getters and Setters
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

    public Object getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(Object actualOutput) {
        this.actualOutput = actualOutput;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}