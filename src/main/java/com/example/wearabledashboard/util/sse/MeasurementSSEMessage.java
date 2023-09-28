package com.example.wearabledashboard.util.sse;

import com.example.wearabledashboard.model.enumerations.Device;

public record MeasurementSSEMessage(
        String ssn,
        Device device
) {
}
