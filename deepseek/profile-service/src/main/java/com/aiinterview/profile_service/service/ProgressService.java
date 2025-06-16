package com.aiinterview.profile_service.service;

import com.aiinterview.profile_service.dto.QuestionDetail;
import com.aiinterview.profile_service.model.UserSubmission;
import com.aiinterview.profile_service.repository.UserSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserSubmissionRepository submissionRepository;

    @Autowired // Explicit constructor injection
    public ProgressService(UserSubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Map<String, QuestionDetail> getProgress(String userId) {
        // Get all correct submissions
        List<UserSubmission> submissions = submissionRepository
                .findByUserIdAndCorrect(userId, true);

        // Map to question details (replace with actual data source)
        return submissions.stream()
                .collect(Collectors.toMap(
                        UserSubmission::getQuestionId,
                        submission -> new QuestionDetail(
                                "Question " + submission.getQuestionId(), // Mock name
                                "Easy" // Mock difficulty
                        )
                ));
    }
}
