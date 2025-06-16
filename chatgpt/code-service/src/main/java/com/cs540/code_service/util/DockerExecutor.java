package com.cs540.code_service.util;

public class DockerExecutor {

    public static String runCode(String language, String code, String input, int timeoutSeconds) {
        // Just simulate output for now (no Docker execution).
        return "Simulated output for language: " + language + ", input: " + input;
    }
}