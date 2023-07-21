package com.example.wearabledashboard.Repository;

import com.example.wearabledashboard.model.Measurement;
import com.example.wearabledashboard.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {


    @Query("select m from Measurement m where m.patient.ssn = :ssn")
    List<Measurement> findByPatientSsn(@Param("ssn")String patientSsn);
}
