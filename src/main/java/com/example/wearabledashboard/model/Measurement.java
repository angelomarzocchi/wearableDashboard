package com.example.wearabledashboard.model;

import com.example.wearabledashboard.dto.MeasurementDTO;
import com.example.wearabledashboard.model.enumerations.Device;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Device device;

    private Date measurementTimestamp;

    private String csvPath;

    @ManyToOne
    private Patient patient;






}
