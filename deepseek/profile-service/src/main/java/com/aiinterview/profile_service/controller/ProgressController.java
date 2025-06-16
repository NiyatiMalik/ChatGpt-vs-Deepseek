package com.aiinterview.profile_service.controller;

import com.aiinterview.profile_service.dto.QuestionDetail;
import com.aiinterview.profile_service.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{userId}/progress")
    public ResponseEntity<Map<String, QuestionDetail>> getProgress(
            @PathVariable String userId
    ) {
        Map<String, QuestionDetail> progress = progressService.getProgress(userId);
        return ResponseEntity.ok(progress);
    }
}
