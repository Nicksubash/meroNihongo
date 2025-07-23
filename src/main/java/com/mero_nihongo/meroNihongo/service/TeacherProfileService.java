package com.mero_nihongo.meroNihongo.service;

import com.mero_nihongo.meroNihongo.dto.TeacherProfileResponse;
import com.mero_nihongo.meroNihongo.dto.TeacherRegistrationRequest;
import com.mero_nihongo.meroNihongo.model.Role;
import com.mero_nihongo.meroNihongo.model.TeacherProfile;
import com.mero_nihongo.meroNihongo.model.User;
import com.mero_nihongo.meroNihongo.repository.TeacherProfileRepository;
import com.mero_nihongo.meroNihongo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherProfileService {

    private final TeacherProfileRepository teacherProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherProfileService(TeacherProfileRepository teacherProfileRepository,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.teacherProfileRepository = teacherProfileRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public TeacherProfileResponse registerTeacher(TeacherRegistrationRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user with TEACHER role
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.TEACHER);
        
        User savedUser = userRepository.save(user);

        // Create teacher profile
        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setUser(savedUser);
        teacherProfile.setBio(request.getBio());
        teacherProfile.setYearsOfExperience(request.getYearsOfExperience());
        teacherProfile.setSpecializations(request.getSpecializations());
        teacherProfile.setSpokenLanguages(request.getSpokenLanguages());
        teacherProfile.setHourlyRate(request.getHourlyRate());
        teacherProfile.setAvailabilityTimezone(request.getAvailabilityTimezone());
        teacherProfile.setIsVerified(false); // New teachers need verification

        TeacherProfile savedProfile = teacherProfileRepository.save(teacherProfile);

        return convertToResponse(savedProfile);
    }

    public TeacherProfileResponse getTeacherProfile(Long userId) {
        TeacherProfile profile = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        return convertToResponse(profile);
    }

    public List<TeacherProfileResponse> getAllVerifiedTeachers() {
        return teacherProfileRepository.findByIsVerified(true)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TeacherProfileResponse> getTeachersByPriceRange(Double minRate, Double maxRate) {
        return teacherProfileRepository.findByHourlyRateBetween(minRate, maxRate)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TeacherProfileResponse> getExperiencedTeachers(Integer minYears) {
        return teacherProfileRepository.findByMinimumExperience(minYears)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeacherProfileResponse updateTeacherProfile(Long userId, TeacherRegistrationRequest request) {
        TeacherProfile profile = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));

        // Update profile fields
        profile.setBio(request.getBio());
        profile.setYearsOfExperience(request.getYearsOfExperience());
        profile.setSpecializations(request.getSpecializations());
        profile.setSpokenLanguages(request.getSpokenLanguages());
        profile.setHourlyRate(request.getHourlyRate());
        profile.setAvailabilityTimezone(request.getAvailabilityTimezone());

        TeacherProfile updatedProfile = teacherProfileRepository.save(profile);
        return convertToResponse(updatedProfile);
    }

    @Transactional
    public void verifyTeacher(Long teacherId) {
        TeacherProfile profile = teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher profile not found"));
        profile.setIsVerified(true);
        teacherProfileRepository.save(profile);
    }

    public boolean isTeacher(Long userId) {
        return teacherProfileRepository.existsByUser(
                userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    private TeacherProfileResponse convertToResponse(TeacherProfile profile) {
        TeacherProfileResponse response = new TeacherProfileResponse();
        response.setId(profile.getId());
        response.setUserId(profile.getUser().getId());
        response.setUsername(profile.getUser().getUsername());
        response.setEmail(profile.getUser().getEmail());
        response.setBio(profile.getBio());
        response.setYearsOfExperience(profile.getYearsOfExperience());
        response.setSpecializations(profile.getSpecializations());
        response.setSpokenLanguages(profile.getSpokenLanguages());
        response.setHourlyRate(profile.getHourlyRate());
        response.setIsVerified(profile.getIsVerified());
        response.setProfileImageUrl(profile.getProfileImageUrl());
        response.setAvailabilityTimezone(profile.getAvailabilityTimezone());
        response.setCreatedAt(profile.getCreatedAt());
        return response;
    }
}
