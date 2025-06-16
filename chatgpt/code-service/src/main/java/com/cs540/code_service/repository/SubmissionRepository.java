package com.cs540.code_service.repository;

import com.cs540.code_service.model.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
    List<Submission> findByUserId(String userId);
    List<Submission> findByProblemId(String problemId);
    List<Submission> findByUserIdAndProblemId(String userId, String problemId);
}
