package com.example.wearabledashboard.Repository;

import com.example.wearabledashboard.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {

    List<Patient> findByDoctorSsn(String doctorSsn);
}
