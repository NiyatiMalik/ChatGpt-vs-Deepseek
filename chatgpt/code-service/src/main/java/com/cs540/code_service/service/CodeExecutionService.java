package com.cs540.code_service.service;

import com.cs540.code_service.model.Submission;
import com.cs540.code_service.model.TestCase;
import com.cs540.code_service.model.TestCaseResult;
import com.cs540.code_service.repository.SubmissionRepository;
import com.cs540.code_service.repository.TestCaseRepository;
import com.cs540.code_service.util.DockerExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeExecutionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    // Full flow: Execute + Validate + Save Submission
    public Submission executeAndSubmit(String language, String code, String userId, String problemId) throws Exception {
        List<TestCase> allTestCases = testCaseRepository.findByProblemId(problemId);

        if (allTestCases.isEmpty()) {
            throw new Exception("No test cases found for problem ID: " + problemId);
        }

        List<TestCaseResult> testCaseResults = new ArrayList<>();
        int passedCount = 0;

        for (TestCase testCase : allTestCases) {
            TestCaseResult result = new TestCaseResult();
            result.setInput(testCase.getInput());
            result.setExpectedOutput(testCase.getExpectedOutput());

            try {
                String inputAsString = String.valueOf(testCase.getInput());
                String expectedOutputAsString = String.valueOf(testCase.getExpectedOutput());

                long startTime = System.currentTimeMillis();
                String output = DockerExecutor.runCode(language, code, inputAsString, testCase.getTimeoutSeconds());
                long endTime = System.currentTimeMillis();

                // 🔥 Now Parse User Output (String) back into Object
                Object parsedActualOutput = parseOutput(output.trim());
                result.setActualOutput(parsedActualOutput);

                // 🔥 Compare based on Object equality
                boolean passed = parsedActualOutput.equals(testCase.getExpectedOutput());
                result.setPassed(passed);
                result.setExecutionTimeMs(endTime - startTime);

                if (passed) passedCount++;
            } catch (Exception e) {
                result.setPassed(false);
                result.setErrorMessage(e.getMessage());
            }

            testCaseResults.add(result);
        }

        // Calculate final score
        double finalScore = ((double) passedCount / allTestCases.size()) * 100.0;

        // Save submission
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setProblemId(problemId);
        submission.setCode(code);
        submission.setTestCaseResults(testCaseResults);
        submission.setFinalScore(finalScore);
        submission.setTimestamp(LocalDateTime.now());

        return submissionRepository.save(submission);
    }

    private Object parseOutput(String output) {
        try {
            // Try parsing as JSON (for arrays, objects, 2D arrays)
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(output, Object.class);
        } catch (Exception e) {
            // If parsing fails, treat as simple String or Integer
            try {
                return Integer.parseInt(output);
            } catch (NumberFormatException ex) {
                return output; // fallback to plain String
            }
        }
    }
}