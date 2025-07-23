package com.mero_nihongo.meroNihongo.controller;

import com.mero_nihongo.meroNihongo.dto.CourseDto;
import com.mero_nihongo.meroNihongo.dto.DashboardResponse;
import com.mero_nihongo.meroNihongo.dto.UserCourseDto;
import com.mero_nihongo.meroNihongo.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).build();
        }

        DashboardResponse dashboard = dashboardService.getDashboardData(username);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = dashboardService.getAllAvailableCourses();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<UserCourseDto> enrollInCourse(@PathVariable Long courseId) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            UserCourseDto userCourse = dashboardService.enrollInCourse(username, courseId);
            return ResponseEntity.ok(userCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                return ((User) principal).getUsername();
            } else if (principal instanceof DefaultOAuth2User) {
                Object email = ((DefaultOAuth2User) principal).getAttributes().get("email");
                if (email instanceof String) {
                    return (String) email;
                }
            }
        }

        return null;
    }
}
