package com.example.wearabledashboard.auth;

import com.example.wearabledashboard.Repository.DoctorRepository;
import com.example.wearabledashboard.config.JwtService;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import com.example.wearabledashboard.exception.UserAlreadyRegisteredException;
import com.example.wearabledashboard.model.Doctor;
import com.example.wearabledashboard.model.enumerations.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final DoctorRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var doctor  = new Doctor();


        if(repository.findBySsn(request.getSsn()).isPresent()) {
            throw new UserAlreadyRegisteredException("Doctor","ssn",request.getSsn());
        }


        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSsn(request.getSsn());
        doctor.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setSpecialization(request.getSpecialization());
        doctor.setGender(request.getGender());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setRole(Role.USER);

        repository.save(doctor);
        var jwtToken = jwtService.generateToken(doctor);
        return AuthenticationResponse.builder()
                     .token(jwtToken)
                        .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getSsn(),request.getPassword())
        );
        //if arrived here user is authenticated
        var doctor = repository.findBySsn(request.getSsn()).orElseThrow();
        var jwtToken = jwtService.generateToken(doctor);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
