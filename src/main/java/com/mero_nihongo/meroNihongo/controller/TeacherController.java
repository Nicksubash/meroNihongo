package com.mero_nihongo.meroNihongo.controller;

import com.mero_nihongo.meroNihongo.dto.TeacherProfileResponse;
import com.mero_nihongo.meroNihongo.dto.TeacherRegistrationRequest;
import com.mero_nihongo.meroNihongo.service.TeacherProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherProfileService teacherProfileService;

    public TeacherController(TeacherProfileService teacherProfileService) {
        this.teacherProfileService = teacherProfileService;
    }

    @PostMapping("/register")
    public ResponseEntity<TeacherProfileResponse> registerTeacher(@RequestBody TeacherRegistrationRequest request) {
        try {
            TeacherProfileResponse response = teacherProfileService.registerTeacher(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<TeacherProfileResponse> getTeacherProfile(@PathVariable Long userId) {
        try {
            TeacherProfileResponse response = teacherProfileService.getTeacherProfile(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<TeacherProfileResponse> updateTeacherProfile(
            @PathVariable Long userId,
            @RequestBody TeacherRegistrationRequest request) {
        try {
            TeacherProfileResponse response = teacherProfileService.updateTeacherProfile(userId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verified")
    public ResponseEntity<List<TeacherProfileResponse>> getVerifiedTeachers() {
        List<TeacherProfileResponse> teachers = teacherProfileService.getAllVerifiedTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeacherProfileResponse>> searchTeachers(
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate,
            @RequestParam(required = false) Integer minExperience) {
        
        List<TeacherProfileResponse> teachers;
        
        if (minRate != null && maxRate != null) {
            teachers = teacherProfileService.getTeachersByPriceRange(minRate, maxRate);
        } else if (minExperience != null) {
            teachers = teacherProfileService.getExperiencedTeachers(minExperience);
        } else {
            teachers = teacherProfileService.getAllVerifiedTeachers();
        }
        
        return ResponseEntity.ok(teachers);
    }

    @PostMapping("/{teacherId}/verify")
    public ResponseEntity<Void> verifyTeacher(@PathVariable Long teacherId) {
        try {
            teacherProfileService.verifyTeacher(teacherId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/is-teacher")
    public ResponseEntity<Boolean> isTeacher(@PathVariable Long userId) {
        boolean isTeacher = teacherProfileService.isTeacher(userId);
        return ResponseEntity.ok(isTeacher);
    }
}
