package com.mero_nihongo.meroNihongo.dto;

import com.mero_nihongo.meroNihongo.model.CourseStatus;
import java.time.LocalDateTime;

public class UserCourseDto {
    private Long id;
    private CourseDto course;
    private LocalDateTime enrolledAt;
    private LocalDateTime lastAccessed;
    private Double progressPercentage;
    private Integer completedLessons;
    private Boolean isCompleted;
    private LocalDateTime completionDate;
    private CourseStatus status;

    public UserCourseDto() {}

    public UserCourseDto(Long id, CourseDto course, LocalDateTime enrolledAt, LocalDateTime lastAccessed,
                        Double progressPercentage, Integer completedLessons, Boolean isCompleted,
                        LocalDateTime completionDate, CourseStatus status) {
        this.id = id;
        this.course = course;
        this.enrolledAt = enrolledAt;
        this.lastAccessed = lastAccessed;
        this.progressPercentage = progressPercentage;
        this.completedLessons = completedLessons;
        this.isCompleted = isCompleted;
        this.completionDate = completionDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Integer getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(Integer completedLessons) {
        this.completedLessons = completedLessons;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
