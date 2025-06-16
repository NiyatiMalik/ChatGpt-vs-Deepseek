package com.aiinterview.profile_service.controller;

import com.aiinterview.profile_service.dto.SubmissionRequest;
import com.aiinterview.profile_service.model.UserSubmission;
import com.aiinterview.profile_service.repository.UserSubmissionRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/submitCode")
public class SubmissionController {

    private final UserSubmissionRepository submissionRepository;

    public SubmissionController(UserSubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @PutMapping
    public ResponseEntity<?> recordOrUpdateSubmission(
            @Valid @RequestBody SubmissionRequest request
    ) {
        // Find existing submission (if any)
        Optional<UserSubmission> existingSubmission = submissionRepository
                .findByUserIdAndQuestionId(request.getUserId(), request.getQuestionId());

        UserSubmission submission;
        if (existingSubmission.isPresent()) {
            // Update existing submission
            submission = existingSubmission.get();
            submission.setCorrect(request.isCorrect());
            submission.setSubmittedCode(request.getSubmittedCode());
            submission.setSubmittedAt(LocalDateTime.now());
        } else {
            // Create new submission
            submission = new UserSubmission();
            submission.setUserId(request.getUserId());
            submission.setQuestionId(request.getQuestionId());
            submission.setCorrect(request.isCorrect());
            submission.setSubmittedCode(request.getSubmittedCode());
        }

        submissionRepository.save(submission);
        return ResponseEntity.ok().build();
    }
}
