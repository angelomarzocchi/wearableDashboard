package com.example.wearabledashboard.service;

import com.example.wearabledashboard.dto.PatientDTO;
import com.example.wearabledashboard.Repository.DoctorRepository;
import com.example.wearabledashboard.Repository.PatientRepository;
import com.example.wearabledashboard.exception.ResourceNotFoundException;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import com.example.wearabledashboard.model.Doctor;
import com.example.wearabledashboard.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private  DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientDTO> getPatients(UserDetails doctor) {

        //return repositoryDoctor.get().getPatients();
        List<Patient> patients = patientRepository.findByDoctorSsn(doctor.getUsername());

        if(patients == null)
            throw new ResourceNotFoundException("Patient","doctorSsn",doctor.getUsername());

        List<PatientDTO> patientsDTO = new ArrayList<>();
        patients.stream().forEach(
                (it) ->
                        patientsDTO.add(new PatientDTO(
                                it.getBloodType(),
                                it.getMedicalHistory(),
                                it.getSsn(),
                                it.getFirstName(),
                                it.getLastName(),
                                it.getDateOfBirth(),
                                it.getGender(),
                                it.getPhoneNumber(),
                                it.getEmail()
                                )
                        )
        );

        return patientsDTO;
    }

    public PatientDTO createPatient(UserDetails doctor, PatientDTO patient) {
        Optional<Doctor> repositoryDoctor = doctorRepository.findBySsn(doctor.getUsername());
        if(repositoryDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor","ssn",doctor.getUsername());
        }
        if(patient == null || patient.ssn() == null ) {
            throw new ResourceNotValidException("Patient","ssn","null value");
        }

        if(patient.ssn().length() > 16) {
            throw new ResourceNotValidException("Patient","ssn",patient.ssn());
        }
        Doctor extractedDoctor = repositoryDoctor.get();
        List<Patient> patients = extractedDoctor.getPatients();
        Patient patientToAdd = new Patient(patient);
        patients.add(patientToAdd);
        extractedDoctor.setPatients(patients);
        patientToAdd.setDoctor(extractedDoctor);
        patientRepository.save(patientToAdd);
        doctorRepository.save(extractedDoctor);
        return patient;
    }



}
