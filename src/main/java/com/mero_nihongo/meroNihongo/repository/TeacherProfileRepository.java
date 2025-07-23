package com.mero_nihongo.meroNihongo.repository;

import com.mero_nihongo.meroNihongo.model.TeacherProfile;
import com.mero_nihongo.meroNihongo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
    
    Optional<TeacherProfile> findByUser(User user);
    
    Optional<TeacherProfile> findByUserId(Long userId);
    
    List<TeacherProfile> findByIsVerified(Boolean isVerified);
    
    @Query("SELECT tp FROM TeacherProfile tp WHERE tp.hourlyRate BETWEEN :minRate AND :maxRate")
    List<TeacherProfile> findByHourlyRateBetween(@Param("minRate") Double minRate, @Param("maxRate") Double maxRate);
    
    @Query("SELECT tp FROM TeacherProfile tp WHERE tp.yearsOfExperience >= :minYears")
    List<TeacherProfile> findByMinimumExperience(@Param("minYears") Integer minYears);
    
    boolean existsByUser(User user);
}
