package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.Course;
import com.mero_nihongo.meroNihongo.model.CourseCategory;
import com.mero_nihongo.meroNihongo.model.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByIsActiveTrue();
    
    List<Course> findByLevelAndIsActiveTrue(CourseLevel level);
    
    List<Course> findByCategoryAndIsActiveTrue(CourseCategory category);
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND " +
           "c.id NOT IN (SELECT uc.course.id FROM UserCourse uc WHERE uc.user.id = :userId)")
    List<Course> findAvailableCoursesForUser(@Param("userId") Long userId);
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND " +
           "c.level = :level AND " +
           "c.id NOT IN (SELECT uc.course.id FROM UserCourse uc WHERE uc.user.id = :userId)")
    List<Course> findRecommendedCoursesByLevel(@Param("userId") Long userId, @Param("level") CourseLevel level);
}
