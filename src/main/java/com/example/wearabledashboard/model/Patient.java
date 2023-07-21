package com.example.wearabledashboard.model;

import com.example.wearabledashboard.dto.PatientDTO;
import com.example.wearabledashboard.model.enumerations.BloodType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Patient extends Person implements Serializable {

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    private String medicalHistory;

    @OneToMany
    private List<Measurement> measurements;

    @ManyToOne
    private Doctor doctor;


public Patient(PatientDTO patientDTO) {
    this.setSsn(patientDTO.ssn());
    this.setBloodType(patientDTO.bloodType());
    this.setMedicalHistory(patientDTO.medicalHistory());
    this.setFirstName(patientDTO.firstName());
    this.setLastName(patientDTO.lastName());
    this.setDateOfBirth(patientDTO.dateOfBirth());
    this.setGender(patientDTO.gender());
    this.setPhoneNumber(patientDTO.phoneNumber());
    this.setEmail(patientDTO.email());
}

public Patient() {}



}
