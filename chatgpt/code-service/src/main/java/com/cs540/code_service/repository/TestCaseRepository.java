package com.cs540.code_service.repository;

import com.cs540.code_service.model.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TestCaseRepository extends MongoRepository<TestCase, String> {
    List<TestCase> findByProblemId(String problemId);
    List<TestCase> findByProblemIdAndHidden(String problemId, boolean hidden);
}