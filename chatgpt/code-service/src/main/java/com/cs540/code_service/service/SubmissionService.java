package com.cs540.code_service.service;

import com.cs540.code_service.model.Submission;
import com.cs540.code_service.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    // Save a submission
    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    // Get all submissions
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    // Get submissions by user ID
    public List<Submission> getSubmissionsByUserId(String userId) {
        return submissionRepository.findByUserId(userId);
    }

    // Get submissions by problem ID
    public List<Submission> getSubmissionsByProblemId(String problemId) {
        return submissionRepository.findByProblemId(problemId);
    }

    // Get submissions by user ID and problem ID (specific problem submission history)
    public List<Submission> getSubmissionsByUserIdAndProblemId(String userId, String problemId) {
        return submissionRepository.findByUserIdAndProblemId(userId, problemId);
    }
}