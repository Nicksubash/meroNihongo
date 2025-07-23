package com.mero_nihongo.meroNihongo.dto;

import com.mero_nihongo.meroNihongo.model.CourseLevel;
import com.mero_nihongo.meroNihongo.model.CourseCategory;

public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private CourseLevel level;
    private CourseCategory category;
    private String imageUrl;
    private Integer totalLessons;
    private Integer estimatedHours;

    public CourseDto() {}

    public CourseDto(Long id, String title, String description, CourseLevel level, 
                    CourseCategory category, String imageUrl, Integer totalLessons, Integer estimatedHours) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.category = category;
        this.imageUrl = imageUrl;
        this.totalLessons = totalLessons;
        this.estimatedHours = estimatedHours;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseLevel getLevel() {
        return level;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public CourseCategory getCategory() {
        return category;
    }

    public void setCategory(CourseCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(Integer totalLessons) {
        this.totalLessons = totalLessons;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
}
