package com.aiinterview.profile_service.repository;

import com.aiinterview.profile_service.model.UserSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubmissionRepository extends JpaRepository<UserSubmission, Long> {
    List<UserSubmission> findByUserId(Long userId);

    Optional<UserSubmission> findByUserIdAndQuestionId(Long userId, String questionId);
}
