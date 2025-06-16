package com.cs540.code_service.controller;

import com.cs540.code_service.model.Submission;
import com.cs540.code_service.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    // Save a new submission
    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionService.saveSubmission(submission);
    }

    // Get all submissions
    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    // Get submissions by userId
    @GetMapping("/user/{userId}")
    public List<Submission> getSubmissionsByUserId(@PathVariable String userId) {
        return submissionService.getSubmissionsByUserId(userId);
    }

    // Get submissions by problemId
    @GetMapping("/problem/{problemId}")
    public List<Submission> getSubmissionsByProblemId(@PathVariable String problemId) {
        return submissionService.getSubmissionsByProblemId(problemId);
    }

    // Get submissions by userId and problemId
    @GetMapping("/user/{userId}/problem/{problemId}")
    public List<Submission> getSubmissionsByUserIdAndProblemId(@PathVariable String userId, @PathVariable String problemId) {
        return submissionService.getSubmissionsByUserIdAndProblemId(userId, problemId);
    }
}