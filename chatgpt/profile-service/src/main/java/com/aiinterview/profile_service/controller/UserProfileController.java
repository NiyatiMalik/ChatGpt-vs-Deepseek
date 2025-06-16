package com.aiinterview.profile_service.controller;

import com.aiinterview.profile_service.model.UserProfile;
import com.aiinterview.profile_service.repository.UserProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userId);
        return userProfile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
                                           @RequestBody UserProfile updatedProfile) {
        return userProfileRepository.findById(userId).map(profile -> {
            profile.setBio(updatedProfile.getBio());
            profile.setProfilePic(updatedProfile.getProfilePic());
            userProfileRepository.save(profile);
            return ResponseEntity.ok(profile);
        }).orElse(ResponseEntity.notFound().build());
    }


}
