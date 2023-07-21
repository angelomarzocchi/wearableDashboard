package com.example.wearabledashboard.dto;

import com.example.wearabledashboard.model.enumerations.BloodType;
import com.example.wearabledashboard.model.enumerations.Gender;

import java.util.Date;

public record PatientDTO(
        BloodType bloodType,
        String medicalHistory,
        String ssn,
        String firstName,
        String lastName,
        Date dateOfBirth,
        Gender gender,
        String phoneNumber,
        String email

) {



}
