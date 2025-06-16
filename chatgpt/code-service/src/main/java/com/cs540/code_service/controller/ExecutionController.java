package com.cs540.code_service.controller;

import com.cs540.code_service.model.Submission;
import com.cs540.code_service.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute")
public class ExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    // Submit code for execution and validation
    @PostMapping
    public Submission executeAndSubmit(@RequestParam String language,
                                       @RequestParam String code,
                                       @RequestParam String userId,
                                       @RequestParam String problemId) throws Exception {
        return codeExecutionService.executeAndSubmit(language, code, userId, problemId);
    }
}