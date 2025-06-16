package com.aiinterview.profile_service.repository;

import com.aiinterview.profile_service.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Find profile by auth service's user ID
    Optional<UserProfile> findByUserId(String userId);

    // Check if profile exists for a user
    boolean existsByUserId(String userId);

}
