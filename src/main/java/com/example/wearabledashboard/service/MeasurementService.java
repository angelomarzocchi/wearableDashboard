package com.example.wearabledashboard.service;

import com.example.wearabledashboard.Repository.DoctorRepository;
import com.example.wearabledashboard.dto.MeasurementDTO;
import com.example.wearabledashboard.dto.PatientDTO;
import com.example.wearabledashboard.Repository.MeasurementRepository;
import com.example.wearabledashboard.Repository.PatientRepository;
import com.example.wearabledashboard.exception.ResourceNotFoundException;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import com.example.wearabledashboard.model.Doctor;
import com.example.wearabledashboard.model.Measurement;
import com.example.wearabledashboard.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MeasurementService {

     static final String PATIENT = "Patient";
     static final String SSN = "ssn";

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<MeasurementDTO> getMeasurements(PatientDTO patient) {

        if(patient == null )
            throw new ResourceNotValidException(PATIENT,PATIENT,"null");
        else if( patient.ssn() == null || patient.ssn().length() == 0)
            throw new ResourceNotValidException(PATIENT,SSN,patient.ssn());

        List<MeasurementDTO> measurements = new ArrayList<>();


        measurementRepository.
                findByPatientSsn(patient.ssn())
                .forEach(it ->
                        measurements.add(
                                new MeasurementDTO(
                                        it.getId(),
                                        it.getDevice(),
                                        it.getMeasurementTimestamp(),
                                        it.getCsvPath(),
                                        patient.ssn()
                                )
                        )
                        );

        //todo al posto dei path trovare i file effettivi e convertirli in stringhe

        return measurements;
    }


    public MeasurementDTO createMeasurement(UserDetails doctor, MeasurementDTO measurement) {

        //Find the doctor in the db
        Optional<Doctor> repositoryDoctor = doctorRepository.findBySsn(doctor.getUsername());
        if(repositoryDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor","ssn",doctor.getUsername());
        }

        //find the patients of the doctor
        repositoryDoctor
                .get()
                .setPatients(
                        patientRepository.
                                findByDoctorSsn(
                                        doctor
                                                .getUsername()
                                )
                               );
        //set the doctor to each patient
        for(Patient patient : repositoryDoctor.get().getPatients())
            patient.setDoctor(repositoryDoctor.get());

        if(measurement == null)
            throw new ResourceNotValidException("Measurement","measurement","null");



        Optional<Patient> repositoryPatient = repositoryDoctor.get().getPatients().stream().filter( it -> it.getSsn().equals( measurement.ssn())).findFirst();

        if(repositoryPatient.isEmpty())
            throw new ResourceNotFoundException(PATIENT,SSN,measurement.ssn());

        Patient patient = repositoryPatient.get();
        patient.setDoctor(repositoryDoctor.get());


        Measurement measurementToAdd = new Measurement();
        measurementToAdd.setDevice(measurement.device());
        measurementToAdd.setCsvPath("C://placeholder/path");
        measurementToAdd.setPatient(patient);
        measurementToAdd.setMeasurementTimestamp(measurement.measurementTimestamp());


        patient.getMeasurements().add(measurementToAdd);
        measurementRepository.save(measurementToAdd);
        patientRepository.save(patient);

        return measurement;



    }
}
