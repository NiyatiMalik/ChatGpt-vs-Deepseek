package com.aiinterview.profile_service.controller;

import com.aiinterview.profile_service.dto.ProgressResponse;
import com.aiinterview.profile_service.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{userId}/progress")
    public ResponseEntity<ProgressResponse> getProgress(@PathVariable Long userId) {
        ProgressResponse progress = progressService.getUserProgress(userId);
        return ResponseEntity.ok(progress);
    }
}
