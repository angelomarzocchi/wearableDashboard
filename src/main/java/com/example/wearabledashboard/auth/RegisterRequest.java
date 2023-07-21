package com.example.wearabledashboard.auth;

import com.example.wearabledashboard.model.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String ssn;
    private String password;

    private Date dateOfBirth;

    private Gender gender;

    private String specialization;
}
