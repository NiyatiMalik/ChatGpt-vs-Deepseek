package com.aiinterview.profile_service.repository;

import com.aiinterview.profile_service.model.UserSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubmissionRepository extends JpaRepository<UserSubmission, Long> {
    List<UserSubmission> findByUserId(String userId);

    // Corrected method name: "Correct" matches the entity field
    List<UserSubmission> findByUserIdAndCorrect(String userId, boolean correct);

    boolean existsByUserIdAndQuestionIdAndCorrect(
            String userId,
            String questionId,
            boolean correct
    );

    Optional<UserSubmission> findByUserIdAndQuestionId(String userId, String questionId);
}
