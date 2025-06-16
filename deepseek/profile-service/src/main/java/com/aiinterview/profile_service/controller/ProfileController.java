package com.aiinterview.profile_service.controller;


import com.aiinterview.profile_service.model.UserProfile;
import com.aiinterview.profile_service.repository.UserProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserProfileRepository userProfileRepository;

    public ProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // 1. Get Profile Endpoint
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable String userId) {
        return userProfileRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2. Update Profile Endpoint
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> updateProfile(
            @PathVariable String userId,
            @RequestBody Map<String, String> requestBody  // Directly accept key-value pairs
    ) {
        return userProfileRepository.findByUserId(userId)
                .map(profile -> {
                    // Update bio if present in request
                    if (requestBody.containsKey("bio")) {
                        profile.setBio(requestBody.get("bio"));
                    }

                    // Update profile picture if present in request
                    if (requestBody.containsKey("profilePic")) {
                        profile.setProfilePictureUrl(requestBody.get("profilePic"));
                    }

                    UserProfile updated = userProfileRepository.save(profile);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
