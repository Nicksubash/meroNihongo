package com.mero_nihongo.meroNihongo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teacher_profiles")
public class TeacherProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 1000)
    private String bio;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "teacher_specializations", joinColumns = @JoinColumn(name = "teacher_profile_id"))
    @Column(name = "specialization")
    private List<JapaneseLevel> specializations;

    @ElementCollection
    @CollectionTable(name = "teacher_languages", joinColumns = @JoinColumn(name = "teacher_profile_id"))
    @Column(name = "language")
    private List<String> spokenLanguages;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "availability_timezone")
    private String availabilityTimezone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public TeacherProfile() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public TeacherProfile(User user, String bio, Integer yearsOfExperience) {
        this();
        this.user = user;
        this.bio = bio;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<JapaneseLevel> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<JapaneseLevel> specializations) {
        this.specializations = specializations;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAvailabilityTimezone() {
        return availabilityTimezone;
    }

    public void setAvailabilityTimezone(String availabilityTimezone) {
        this.availabilityTimezone = availabilityTimezone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
