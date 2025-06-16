package com.aiinterview.profile_service.repository;

import com.aiinterview.profile_service.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {



}
