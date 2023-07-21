package com.example.wearabledashboard.controller;


import com.example.wearabledashboard.dto.PatientDTO;
import com.example.wearabledashboard.exception.ResourceNotFoundException;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import com.example.wearabledashboard.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("bodybrainic/api/doc")
public class PatientController {

    @Autowired
    private PatientService service;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getPatients() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            List<PatientDTO> patients = service.getPatients(userDetails);
            return ResponseEntity.ok(patients);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }


    }


    @PostMapping("/patients")
    public ResponseEntity<String> addPatient(RequestEntity<PatientDTO> request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            service.createPatient(userDetails,request.getBody());
            return ResponseEntity.ok("Patient added correctly");
        } catch (ResourceNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
        catch(ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }







}
