// SubmissionRequest.java
package com.cs540.code_service.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubmissionRequest extends ExecutionRequest {
    @NotNull
    private List<TestCase> hiddenTestCases = new ArrayList<>();

    // Constructor for convenience
    public SubmissionRequest(String code, String language, String userId,
                             String problemId, List<TestCase> hiddenTestCases) {
        this.setCode(code);
        this.setLanguage(language);
        this.setUserId(userId);
        this.setProblemId(problemId);
        this.hiddenTestCases = hiddenTestCases != null ? hiddenTestCases : new ArrayList<>();
    }
}