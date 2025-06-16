package com.aiinterview.profile_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private Long id; // same as userId from auth service

    @Column(columnDefinition = "TEXT")
    private String bio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Column(name = "profile_pic")
    private String profilePic;
}
