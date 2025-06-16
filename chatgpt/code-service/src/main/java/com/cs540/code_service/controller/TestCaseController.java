package com.cs540.code_service.controller;

import com.cs540.code_service.model.TestCase;
import com.cs540.code_service.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    // Create or Update a Test Case
    @PostMapping
    public TestCase createTestCase(@RequestBody TestCase testCase) {
        return testCaseService.saveTestCase(testCase);
    }

    // Get all Test Cases
    @GetMapping
    public List<TestCase> getAllTestCases() {
        return testCaseService.getAllTestCases();
    }

    // Get Test Cases by problemId
    @GetMapping("/problem/{problemId}")
    public List<TestCase> getTestCasesByProblemId(@PathVariable String problemId) {
        return testCaseService.getTestCasesByProblemId(problemId);
    }

    // Get visible/hidden Test Cases by problemId
    @GetMapping("/problem/{problemId}/visibility/{hidden}")
    public List<TestCase> getTestCasesByProblemIdAndVisibility(@PathVariable String problemId, @PathVariable boolean hidden) {
        return testCaseService.getTestCasesByProblemIdAndVisibility(problemId, hidden);
    }

    // Delete a Test Case by ID
    @DeleteMapping("/{id}")
    public String deleteTestCase(@PathVariable String id) {
        boolean deleted = testCaseService.deleteTestCaseById(id);
        if (deleted) {
            return "Test Case deleted successfully.";
        } else {
            return "Test Case not found.";
        }
    }
}