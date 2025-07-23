package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.UserCourse;
import com.mero_nihongo.meroNihongo.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    
    List<UserCourse> findByUserIdOrderByLastAccessedDesc(Long userId);
    
    List<UserCourse> findByUserIdAndStatus(Long userId, CourseStatus status);
    
    Optional<UserCourse> findByUserIdAndCourseId(Long userId, Long courseId);
    
    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.id = :userId AND uc.status = 'ACTIVE' ORDER BY uc.lastAccessed DESC")
    List<UserCourse> findActiveCoursesForUser(@Param("userId") Long userId);
    
    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.id = :userId AND uc.isCompleted = true ORDER BY uc.completionDate DESC")
    List<UserCourse> findCompletedCoursesForUser(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(uc) FROM UserCourse uc WHERE uc.user.id = :userId AND uc.isCompleted = true")
    Long countCompletedCoursesByUser(@Param("userId") Long userId);
    
    @Query("SELECT AVG(uc.progressPercentage) FROM UserCourse uc WHERE uc.user.id = :userId AND uc.status = 'ACTIVE'")
    Double getAverageProgressForUser(@Param("userId") Long userId);
}
