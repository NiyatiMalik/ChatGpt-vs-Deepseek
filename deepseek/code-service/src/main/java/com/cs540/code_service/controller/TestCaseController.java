package com.cs540.code_service.controller;

import com.cs540.code_service.model.TestCase;
import com.cs540.code_service.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @PostMapping
    public ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase) {
        TestCase savedTestCase = testCaseRepository.save(testCase);
        return ResponseEntity
                .created(URI.create("/api/testcases/" + savedTestCase.getId()))
                .body(savedTestCase);
    }

    @GetMapping
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable String id) {
        return testCaseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(
            @PathVariable String id,
            @RequestBody TestCase testCase) {
        return testCaseRepository.findById(id)
                .map(existing -> {
                    testCase.setId(id);
                    return ResponseEntity.ok(testCaseRepository.save(testCase));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTestCase(@PathVariable String id) {
        return testCaseRepository.findById(id)
                .map(testCase -> {
                    testCaseRepository.delete(testCase);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(
                MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
    }
}