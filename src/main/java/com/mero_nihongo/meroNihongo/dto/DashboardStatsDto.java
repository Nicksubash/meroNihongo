package com.mero_nihongo.meroNihongo.dto;

public class DashboardStatsDto {
    private Long totalCoursesEnrolled;
    private Long completedCourses;
    private Double averageProgress;
    private Integer totalLessonsCompleted;

    public DashboardStatsDto() {}

    public DashboardStatsDto(Long totalCoursesEnrolled, Long completedCourses, 
                           Double averageProgress, Integer totalLessonsCompleted) {
        this.totalCoursesEnrolled = totalCoursesEnrolled;
        this.completedCourses = completedCourses;
        this.averageProgress = averageProgress;
        this.totalLessonsCompleted = totalLessonsCompleted;
    }

    // Getters and Setters
    public Long getTotalCoursesEnrolled() {
        return totalCoursesEnrolled;
    }

    public void setTotalCoursesEnrolled(Long totalCoursesEnrolled) {
        this.totalCoursesEnrolled = totalCoursesEnrolled;
    }

    public Long getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(Long completedCourses) {
        this.completedCourses = completedCourses;
    }

    public Double getAverageProgress() {
        return averageProgress;
    }

    public void setAverageProgress(Double averageProgress) {
        this.averageProgress = averageProgress;
    }

    public Integer getTotalLessonsCompleted() {
        return totalLessonsCompleted;
    }

    public void setTotalLessonsCompleted(Integer totalLessonsCompleted) {
        this.totalLessonsCompleted = totalLessonsCompleted;
    }
}
