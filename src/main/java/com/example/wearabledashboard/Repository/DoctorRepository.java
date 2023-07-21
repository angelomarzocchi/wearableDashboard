package com.example.wearabledashboard.Repository;

import com.example.wearabledashboard.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Optional<Doctor> findBySsn(String ssn);
}
