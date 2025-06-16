package com.cs540.code_service.service;

import com.cs540.code_service.model.TestCase;
import com.cs540.code_service.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    // Create or Update a TestCase
    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    // Get all test cases
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    // Get test cases by problem ID
    public List<TestCase> getTestCasesByProblemId(String problemId) {
        return testCaseRepository.findByProblemId(problemId);
    }

    // Get visible/hidden test cases for a problem
    public List<TestCase> getTestCasesByProblemIdAndVisibility(String problemId, boolean hidden) {
        return testCaseRepository.findByProblemIdAndHidden(problemId, hidden);
    }

    // Delete a test case by ID
    public boolean deleteTestCaseById(String id) {
        Optional<TestCase> optionalTestCase = testCaseRepository.findById(id);
        if (optionalTestCase.isPresent()) {
            testCaseRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}