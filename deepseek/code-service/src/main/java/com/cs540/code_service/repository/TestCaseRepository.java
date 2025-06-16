package com.cs540.code_service.repository;

import com.cs540.code_service.model.TestCase;
import com.mongodb.lang.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestCaseRepository extends MongoRepository<TestCase, String> {
    // Add null check annotation
    @Nullable
    List<TestCase> findByProblemIdAndHidden(String problemId, boolean hidden);
}
