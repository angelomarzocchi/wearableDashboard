package com.example.wearabledashboard.auth;

import com.example.wearabledashboard.exception.UserAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bodybrainic/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity< AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (UserAlreadyRegisteredException exception) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().build());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
