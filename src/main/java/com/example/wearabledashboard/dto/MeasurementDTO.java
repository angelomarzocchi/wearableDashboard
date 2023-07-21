package com.example.wearabledashboard.dto;

import com.example.wearabledashboard.model.enumerations.Device;

import java.util.Date;

public record MeasurementDTO(
        Integer id,
        Device device,
        Date measurementTimestamp,
        String csv,

        String ssn
) {
}
