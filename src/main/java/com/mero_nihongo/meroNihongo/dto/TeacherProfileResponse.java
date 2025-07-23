package com.mero_nihongo.meroNihongo.dto;

import com.mero_nihongo.meroNihongo.model.JapaneseLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherProfileResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String bio;
    private Integer yearsOfExperience;
    private List<JapaneseLevel> specializations;
    private List<String> spokenLanguages;
    private Double hourlyRate;
    private Boolean isVerified;
    private String profileImageUrl;
    private String availabilityTimezone;
    private LocalDateTime createdAt;
}
