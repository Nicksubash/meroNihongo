package com.mero_nihongo.meroNihongo.dto;

import java.util.List;

public class DashboardResponse {
    private UserProfileDto userProfile;
    private List<UserCourseDto> activeCourses;
    private List<CourseDto> recommendedCourses;
    private DashboardStatsDto stats;

    public DashboardResponse() {}

    public DashboardResponse(UserProfileDto userProfile, List<UserCourseDto> activeCourses, 
                           List<CourseDto> recommendedCourses, DashboardStatsDto stats) {
        this.userProfile = userProfile;
        this.activeCourses = activeCourses;
        this.recommendedCourses = recommendedCourses;
        this.stats = stats;
    }

    // Getters and Setters
    public UserProfileDto getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDto userProfile) {
        this.userProfile = userProfile;
    }

    public List<UserCourseDto> getActiveCourses() {
        return activeCourses;
    }

    public void setActiveCourses(List<UserCourseDto> activeCourses) {
        this.activeCourses = activeCourses;
    }

    public List<CourseDto> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<CourseDto> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

    public DashboardStatsDto getStats() {
        return stats;
    }

    public void setStats(DashboardStatsDto stats) {
        this.stats = stats;
    }
}
