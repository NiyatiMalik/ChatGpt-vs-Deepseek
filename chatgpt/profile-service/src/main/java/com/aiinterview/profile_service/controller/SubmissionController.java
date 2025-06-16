package com.aiinterview.profile_service.controller;

import com.aiinterview.profile_service.dto.SubmissionRequest;
import com.aiinterview.profile_service.model.UserSubmission;
import com.aiinterview.profile_service.repository.UserSubmissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> upsertSubmission(@RequestBody SubmissionRequest request) {
        Optional<UserSubmission> existing = submissionRepository
                .findByUserIdAndQuestionId(request.getUserId(), request.getQuestionId());

        UserSubmission submission = existing.orElseGet(UserSubmission::new);
        existing.ifPresent(existingSubmission -> submission.setId(existingSubmission.getId()));

        submission.setUserId(request.getUserId());
        submission.setQuestionId(request.getQuestionId());
        submission.setCorrect(request.isCorrect());
        submission.setSubmittedCode(request.getSubmittedCode());
        submission.setTimestamp(LocalDateTime.now());

        UserSubmission saved = submissionRepository.save(submission);
        return ResponseEntity.ok(saved);
    }

}

