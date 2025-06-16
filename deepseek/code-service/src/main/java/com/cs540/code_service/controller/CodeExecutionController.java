package com.cs540.code_service.controller;

import com.cs540.code_service.model.ExecutionRequest;
import com.cs540.code_service.model.ExecutionResult;
import com.cs540.code_service.model.SubmissionRequest;
import com.cs540.code_service.model.SubmissionResult;
import com.cs540.code_service.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/execute")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService executionService;

    @PostMapping("/run")
    public ResponseEntity<ExecutionResult> executeCode(@RequestBody ExecutionRequest request) {
        ExecutionResult result = executionService.executeCode(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResult> submitCode(@Valid @RequestBody SubmissionRequest request){
        SubmissionResult result = executionService.submitCode(request);
        return ResponseEntity.ok(result);
    }
}
