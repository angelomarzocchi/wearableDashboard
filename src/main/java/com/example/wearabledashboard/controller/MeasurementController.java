package com.example.wearabledashboard.controller;

import com.example.wearabledashboard.dto.MeasurementDTO;
import com.example.wearabledashboard.dto.PatientDTO;
import com.example.wearabledashboard.exception.ResourceNotFoundException;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import com.example.wearabledashboard.service.MeasurementService;
import com.example.wearabledashboard.util.sse.MeasurementSSEMessage;
import com.example.wearabledashboard.util.sse.SSEUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Transactional
@RequestMapping("/bodybrainic/api/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService service;

    @Autowired
    private SSEUtil<MeasurementSSEMessage> sseUtil;

    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getMeasurements(
            RequestEntity<PatientDTO> request) {
        try {
            List<MeasurementDTO> measurements = service.getMeasurements(request.getBody());
            return ResponseEntity.ok(measurements);
        } catch (ResourceNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path="/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<MeasurementSSEMessage>> subscribeToSSE() {
        return sseUtil.subscribe();
    }


    @PostMapping
    public ResponseEntity<String> addMeasurement(RequestEntity<MeasurementDTO> request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            service.createMeasurement(userDetails,request.getBody());
            sseUtil.emitSignal(new MeasurementSSEMessage(Objects.requireNonNull(request.getBody()).getSsn(),request.getBody().getDevice()));
            return ResponseEntity.ok("Measurement added correctly");
        } catch (ResourceNotValidException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();

        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
