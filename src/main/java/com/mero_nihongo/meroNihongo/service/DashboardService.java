package com.mero_nihongo.meroNihongo.service;

import com.mero_nihongo.meroNihongo.dto.*;
import com.mero_nihongo.meroNihongo.model.*;
import com.mero_nihongo.meroNihongo.repository.CourseRepository;
import com.mero_nihongo.meroNihongo.repository.UserCourseRepository;
import com.mero_nihongo.meroNihongo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public DashboardService(UserRepository userRepository, CourseRepository courseRepository, 
                          UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userCourseRepository = userCourseRepository;
    }

    public DashboardResponse getDashboardData(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDto userProfile = convertToUserProfileDto(user);
        List<UserCourseDto> activeCourses = getActiveCoursesForUser(user.getId());
        List<CourseDto> recommendedCourses = getRecommendedCoursesForUser(user.getId());
        DashboardStatsDto stats = getDashboardStats(user.getId());

        return new DashboardResponse(userProfile, activeCourses, recommendedCourses, stats);
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        return new UserProfileDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    private List<UserCourseDto> getActiveCoursesForUser(Long userId) {
        List<UserCourse> activeCourses = userCourseRepository.findActiveCoursesForUser(userId);
        return activeCourses.stream()
                .map(this::convertToUserCourseDto)
                .collect(Collectors.toList());
    }

    private List<CourseDto> getRecommendedCoursesForUser(Long userId) {
        // Get user's completed courses to determine their level
        List<UserCourse> completedCourses = userCourseRepository.findCompletedCoursesForUser(userId);
        
        CourseLevel recommendedLevel = determineRecommendedLevel(completedCourses);
        
        List<Course> recommendedCourses = courseRepository.findRecommendedCoursesByLevel(userId, recommendedLevel);
        
        // Limit to 6 recommendations
        return recommendedCourses.stream()
                .limit(6)
                .map(this::convertToCourseDto)
                .collect(Collectors.toList());
    }

    private CourseLevel determineRecommendedLevel(List<UserCourse> completedCourses) {
        if (completedCourses.isEmpty()) {
            return CourseLevel.BEGINNER;
        }
        
        // Simple logic: if user has completed advanced courses, recommend expert
        // if completed intermediate, recommend advanced, etc.
        boolean hasExpert = completedCourses.stream()
                .anyMatch(uc -> uc.getCourse().getLevel() == CourseLevel.EXPERT);
        boolean hasAdvanced = completedCourses.stream()
                .anyMatch(uc -> uc.getCourse().getLevel() == CourseLevel.ADVANCED);
        boolean hasIntermediate = completedCourses.stream()
                .anyMatch(uc -> uc.getCourse().getLevel() == CourseLevel.INTERMEDIATE);
        
        if (hasExpert) return CourseLevel.EXPERT;
        if (hasAdvanced) return CourseLevel.EXPERT;
        if (hasIntermediate) return CourseLevel.ADVANCED;
        return CourseLevel.INTERMEDIATE;
    }

    private DashboardStatsDto getDashboardStats(Long userId) {
        List<UserCourse> allUserCourses = userCourseRepository.findByUserIdOrderByLastAccessedDesc(userId);
        
        Long totalEnrolled = (long) allUserCourses.size();
        Long completedCourses = userCourseRepository.countCompletedCoursesByUser(userId);
        Double averageProgress = userCourseRepository.getAverageProgressForUser(userId);
        
        Integer totalLessonsCompleted = allUserCourses.stream()
                .mapToInt(UserCourse::getCompletedLessons)
                .sum();

        return new DashboardStatsDto(totalEnrolled, completedCourses, 
                                   averageProgress != null ? averageProgress : 0.0, totalLessonsCompleted);
    }

    private UserCourseDto convertToUserCourseDto(UserCourse userCourse) {
        CourseDto courseDto = convertToCourseDto(userCourse.getCourse());
        return new UserCourseDto(
                userCourse.getId(),
                courseDto,
                userCourse.getEnrolledAt(),
                userCourse.getLastAccessed(),
                userCourse.getProgressPercentage(),
                userCourse.getCompletedLessons(),
                userCourse.getIsCompleted(),
                userCourse.getCompletionDate(),
                userCourse.getStatus()
        );
    }

    private CourseDto convertToCourseDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getLevel(),
                course.getCategory(),
                course.getImageUrl(),
                course.getTotalLessons(),
                course.getEstimatedHours()
        );
    }

    public UserCourseDto enrollInCourse(String username, Long courseId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check if already enrolled
        if (userCourseRepository.findByUserIdAndCourseId(user.getId(), courseId).isPresent()) {
            throw new RuntimeException("User already enrolled in this course");
        }

        UserCourse userCourse = new UserCourse(user, course);
        userCourse = userCourseRepository.save(userCourse);

        return convertToUserCourseDto(userCourse);
    }

    public List<CourseDto> getAllAvailableCourses() {
        List<Course> courses = courseRepository.findByIsActiveTrue();
        return courses.stream()
                .map(this::convertToCourseDto)
                .collect(Collectors.toList());
    }
}
