package com.cs540.code_service.service;

import com.cs540.code_service.model.*;
import com.cs540.code_service.repository.SubmissionRepository;
import com.cs540.code_service.repository.TestCaseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutionService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Value("${execution.max-timeout-seconds:5}")
    private int defaultTimeout;

    @Value("${execution.workspace:/tmp/code_execution}")
    private String workspacePath;

    private ExecutionResult mapToExecutionResult(ProcessExecutionResult processResult) {
        ExecutionResult result = new ExecutionResult();
        result.setOutput(processResult.getOutput());
        result.setError(processResult.getError());
        result.setExecutionTime(processResult.getExecutionTime());
        result.setMemoryUsage(processResult.getMemoryUsage());
        result.setStatus(determineStatus(processResult));
        return result;
    }

    private String determineStatus(ProcessExecutionResult processResult) {
        if (processResult.isTimeout()) {
            return "TIMEOUT";
        } else if (processResult.getExitCode() != 0) {
            return processResult.getError().contains("error") ? "COMPILATION_ERROR" : "RUNTIME_ERROR";
        }
        return "SUCCESS";
    }

    private String determineTestCaseStatus(ExecutionResult executionResult, TestCase testCase) {
        if (!"SUCCESS".equals(executionResult.getStatus())) {
            return executionResult.getStatus();
        }

        // Convert both outputs to String and normalize them before comparison
        String actualOutput = normalizeOutput(executionResult.getOutput());
        String expectedOutput = normalizeOutput(convertObjectToString(testCase.getExpectedOutput()));

        return actualOutput.equals(expectedOutput) ? "PASSED" : "FAILED";
    }

    private String normalizeOutput(String output) {
        return output != null ? output.trim().replaceAll("\\r\\n", "\n") : "";
    }

    public ExecutionResult executeCode(ExecutionRequest request) {
        try {
            // Create workspace directory
            Path workspace = Paths.get(workspacePath);
            Files.createDirectories(workspace);

            // Prepare files
            String filename = prepareCodeFile(workspace, request.getCode(), request.getLanguage());

            // Execute code
            ProcessExecutionResult processResult = executeProcess(
                    getExecutionCommand(filename, request.getLanguage()),
                    request.getInput(),
                    request.getTimeoutSeconds() > 0 ? request.getTimeoutSeconds() : defaultTimeout
            );

            // Clean up
            cleanupWorkspace(workspace);

            return mapToExecutionResult(processResult);
        } catch (Exception e) {
            ExecutionResult errorResult = new ExecutionResult();
            errorResult.setStatus("ERROR");
            errorResult.setError(e.getMessage());
            return errorResult;
        }
    }

    public SubmissionResult submitCode(SubmissionRequest request) {
        // Get test cases (visible + hidden)
        List<TestCase> visibleTestCases = testCaseRepository.findByProblemIdAndHidden(request.getProblemId(), false);
        List<TestCase> allTestCases = new ArrayList<>();

        if (visibleTestCases != null) {
            allTestCases.addAll(visibleTestCases);
        }

        if (request.getHiddenTestCases() != null) {
            allTestCases.addAll(request.getHiddenTestCases());
        }

        // Handle empty test cases scenario
        if (allTestCases.isEmpty()) {
            return createEmptySubmissionResult();
        }

        List<TestCaseResult> results = processTestCases(request, allTestCases);
        return createSubmissionResult(results);
    }

    private SubmissionResult createEmptySubmissionResult() {
        SubmissionResult result = new SubmissionResult();
        result.setTestCaseResults(Collections.emptyList());
        result.setPassedCount(0);
        result.setTotalCount(0);
        result.setScore(0.0);
        result.setOverallStatus("NO_TEST_CASES");
        result.setAllPassed(false);
        return result;
    }

    private List<TestCaseResult> processTestCases(SubmissionRequest request, List<TestCase> testCases) {
        List<TestCaseResult> results = new ArrayList<>();

        for (TestCase testCase : testCases) {
            ExecutionRequest executionRequest = createExecutionRequest(request, testCase);
            ExecutionResult executionResult = executeCode(executionRequest);

            TestCaseResult testCaseResult = createTestCaseResult(testCase, executionResult);
            results.add(testCaseResult);
        }

        return results;
    }

    private ExecutionRequest createExecutionRequest(SubmissionRequest request, TestCase testCase) {
        ExecutionRequest executionRequest = new ExecutionRequest();
        executionRequest.setCode(request.getCode());
        executionRequest.setLanguage(request.getLanguage());

        // Convert input to String
        Object testCaseInput = testCase.getInput();
        String inputString = "";
        if (testCaseInput != null) {
            if (testCaseInput instanceof String) {
                inputString = (String) testCaseInput;
            } else {
                try {
                    inputString = objectMapper.writeValueAsString(testCaseInput);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to serialize test case input", e);
                }
            }
        }
        executionRequest.setInput(inputString);

        executionRequest.setTimeoutSeconds(testCase.getTimeoutSeconds());
        executionRequest.setUserId(request.getUserId());
        executionRequest.setProblemId(request.getProblemId());
        return executionRequest;
    }

    private TestCaseResult createTestCaseResult(TestCase testCase, ExecutionResult executionResult) {
        boolean passed = isTestCasePassed(testCase, executionResult);

        // Convert input and expectedOutput to String
        String inputString = convertObjectToString(testCase.getInput());
        String expectedOutputString = convertObjectToString(testCase.getExpectedOutput());

        return new TestCaseResult(
                inputString,
                expectedOutputString,
                executionResult.getOutput(),
                passed ? "PASSED" : "FAILED",
                executionResult.getExecutionTime(),
                executionResult.getMemoryUsage(),
                executionResult.getError(),
                testCase.isHidden()
        );
    }

    private String convertObjectToString(Object obj) {
        if (obj == null) return "";
        if (obj instanceof String) return (String) obj;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    // Add this at class level
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Modify the isTestCasePassed method
    private boolean isTestCasePassed(TestCase testCase, ExecutionResult executionResult) {
        if (!"SUCCESS".equals(executionResult.getStatus())) {
            return false;
        }

        try {
            String expected = objectMapper.writeValueAsString(testCase.getExpectedOutput());
            String actual = executionResult.getOutput().trim();

            // Try parsing as JSON if possible
            try {
                Object expectedObj = objectMapper.readValue(expected, Object.class);
                Object actualObj = objectMapper.readValue(actual, Object.class);
                return expectedObj.equals(actualObj);
            } catch (Exception e) {
                // Fall back to string comparison
                return normalizeOutput(expected).equals(normalizeOutput(actual));
            }
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    private SubmissionResult createSubmissionResult(List<TestCaseResult> results) {
        int passedCount = calculatePassedCount(results);
        int totalCount = results.size();
        double score = calculateScore(passedCount, totalCount);
        String overallStatus = determineOverallStatus(passedCount, totalCount);
        boolean allPassed = (passedCount == totalCount);

        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setTestCaseResults(results);
        submissionResult.setPassedCount(passedCount);
        submissionResult.setTotalCount(totalCount);
        submissionResult.setScore(score);
        submissionResult.setOverallStatus(overallStatus);
        submissionResult.setAllPassed(allPassed);

        return submissionResult;
    }

    private int calculatePassedCount(List<TestCaseResult> results) {
        return (int) results.stream()
                .filter(r -> "PASSED".equals(r.getStatus()))
                .count();
    }

    private double calculateScore(int passedCount, int totalCount) {
        return totalCount == 0 ? 0.0 : ((double) passedCount / totalCount) * 100;
    }

    private String determineOverallStatus(int passedCount, int totalCount) {
        return totalCount == 0 ? "NO_TEST_CASES" :
                passedCount == totalCount ? "ACCEPTED" : "REJECTED";
    }

    private String prepareCodeFile(Path workspace, String code, String language) throws IOException {
        String filename = getFilename(language);
        Path filePath = workspace.resolve(filename);
        Files.write(filePath, code.getBytes());
        return filePath.toString();
    }

    private ProcessExecutionResult executeProcess(String command, Object input, int timeoutSeconds)
            throws IOException, InterruptedException {

        // Convert input to String
        String stringInput = "";
        if (input != null) {
            if (input instanceof String) {
                stringInput = (String) input;
            } else {
                try {
                    stringInput = objectMapper.writeValueAsString(input);
                } catch (JsonProcessingException e) {
                    throw new IOException("Failed to serialize input", e);
                }
            }
        }

        long startTime = System.currentTimeMillis();
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("sh", "-c", command);
        }

        Process process = processBuilder.start();

        // Write input if provided
        if (!stringInput.isEmpty()) {
            try (OutputStream outputStream = process.getOutputStream()) {
                outputStream.write(stringInput.getBytes());
                outputStream.flush();
            }
        }

        // Rest of the method remains the same...
        StringBuilder output = new StringBuilder();
        StringBuilder error = new StringBuilder();

        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException e) {
                error.append("Error reading output: ").append(e.getMessage());
            }
        });

        Thread errorThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    error.append(line).append("\n");
                }
            } catch (IOException e) {
                error.append("Error reading error stream: ").append(e.getMessage());
            }
        });

        outputThread.start();
        errorThread.start();

        boolean completed = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
        if (!completed) {
            process.destroyForcibly();
            return new ProcessExecutionResult(
                    true,
                    -1,
                    output.toString(),
                    "Execution timed out after " + timeoutSeconds + " seconds",
                    System.currentTimeMillis() - startTime,
                    0
            );
        }

        outputThread.join(1000);
        errorThread.join(1000);

        long executionTime = System.currentTimeMillis() - startTime;

        return new ProcessExecutionResult(
                false,
                process.exitValue(),
                output.toString(),
                error.toString(),
                executionTime,
                0
        );
    }

    private void cleanupWorkspace(Path workspace) throws IOException {
        Files.walk(workspace)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Log warning
                    }
                });
    }

    private String getFilename(String language) {
        switch (language) {
            case "java": return "Main.java";
            case "python": return "main.py";
            case "cpp": return "main.cpp";
            default: throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private String getExecutionCommand(String filename, String language) {
        Path filePath = Paths.get(filename);
        Path parent = filePath.getParent();
        String baseName = filePath.getFileName().toString().replaceFirst("[.][^.]+$", "");

        switch (language) {
            case "java":
                return "javac " + filename + " && java -cp " + parent + " " + baseName;
            case "python":
                return "python " + filename;
            case "cpp":
                String executable = parent.resolve(baseName).toString();
                return "g++ " + filename + " -o " + executable + " && " + executable;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}