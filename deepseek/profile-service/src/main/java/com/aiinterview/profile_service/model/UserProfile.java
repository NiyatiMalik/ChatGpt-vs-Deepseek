package com.aiinterview.profile_service.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_profile_deepseek")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // BIGINT PK (auto-increment)

    @Column(nullable = false, unique = true)
    private String userId;  // From Auth Service (not null)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "profile_pic")
    private String profilePictureUrl;  // VARCHAR(255) by default

}
