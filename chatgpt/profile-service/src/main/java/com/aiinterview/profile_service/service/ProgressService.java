package com.aiinterview.profile_service.service;

import com.aiinterview.profile_service.dto.ProgressResponse;
import com.aiinterview.profile_service.model.UserSubmission;
import com.aiinterview.profile_service.repository.UserSubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {

    private final UserSubmissionRepository submissionRepository;
    private final RestTemplate restTemplate; // We'll inject this

    public ProgressService(UserSubmissionRepository submissionRepository, RestTemplate restTemplate) {
        this.submissionRepository = submissionRepository;
        this.restTemplate = restTemplate;
    }

    public ProgressResponse getUserProgress(Long userId) {
        List<UserSubmission> correctSubmissions = submissionRepository.findByUserId(userId)
                .stream()
                .filter(UserSubmission::isCorrect)
                .toList();

        int totalSolved = correctSubmissions.size();

        Map<String, ProgressResponse.QuestionInfo> questionInfoMap = new HashMap<>();

        // Assuming question-service gives you details in one call
        for (UserSubmission submission : correctSubmissions) {
            String questionId = submission.getQuestionId();

            // Call question-service (dummy URL for now)
            Map<String, String> questionDetails = restTemplate.getForObject(
                    "http://localhost:8081/questions/" + questionId,
                    Map.class
            );

            questionInfoMap.put(questionId, new ProgressResponse.QuestionInfo(
                    (String) questionDetails.get("name"),
                    (String) questionDetails.get("difficulty")
            ));
        }

        return new ProgressResponse(totalSolved, questionInfoMap);
    }
}