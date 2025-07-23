package com.mero_nihongo.meroNihongo.dto;

import com.mero_nihongo.meroNihongo.model.JapaneseLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegistrationRequest {
    // Basic user info
    private String username;
    private String email;
    private String password;
    
    // Teacher profile info
    private String bio;
    private Integer yearsOfExperience;
    private List<JapaneseLevel> specializations;
    private List<String> spokenLanguages;
    private Double hourlyRate;
    private String availabilityTimezone;
}
