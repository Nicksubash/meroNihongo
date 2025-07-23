package com.mero_nihongo.meroNihongo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_courses")
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    @Column(name = "last_accessed")
    private LocalDateTime lastAccessed;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    @Column(name = "completed_lessons")
    private Integer completedLessons = 0;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Enumerated(EnumType.STRING)
    private CourseStatus status = CourseStatus.ACTIVE;

    // Constructors
    public UserCourse() {
        this.enrolledAt = LocalDateTime.now();
        this.lastAccessed = LocalDateTime.now();
    }

    public UserCourse(User user, Course course) {
        this();
        this.user = user;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
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
        if (isCompleted && completionDate == null) {
            this.completionDate = LocalDateTime.now();
        }
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

    public void updateProgress(int completedLessons, int totalLessons) {
        this.completedLessons = completedLessons;
        this.progressPercentage = totalLessons > 0 ? (double) completedLessons / totalLessons * 100 : 0.0;
        this.lastAccessed = LocalDateTime.now();
        
        if (completedLessons >= totalLessons) {
            this.isCompleted = true;
            this.completionDate = LocalDateTime.now();
            this.status = CourseStatus.COMPLETED;
        }
    }
}
