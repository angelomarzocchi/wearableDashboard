package com.example.wearabledashboard.dto;

import com.example.wearabledashboard.model.enumerations.Device;

import java.util.Date;
import java.util.Objects;

public final class MeasurementDTO {
    private final Integer id;
    private final Device device;
    private final Date measurementTimestamp;

    private  String csv;
    private final String ssn;

    public MeasurementDTO(
            Integer id,
            Device device,
            Date measurementTimestamp,
            String csv,

            String ssn
    ) {
        this.id = id;
        this.device = device;
        this.measurementTimestamp = measurementTimestamp;
        this.csv = csv;
        this.ssn = ssn;
    }








    public void setCsv(String csv) {
        this.csv = csv;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MeasurementDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.device, that.device) &&
                Objects.equals(this.measurementTimestamp, that.measurementTimestamp) &&
                Objects.equals(this.csv, that.csv) &&
                Objects.equals(this.ssn, that.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, device, measurementTimestamp, csv, ssn);
    }

    @Override
    public String toString() {
        return "MeasurementDTO[" +
                "id=" + id + ", " +
                "device=" + device + ", " +
                "measurementTimestamp=" + measurementTimestamp + ", " +
                "csv=" + csv + ", " +
                "ssn=" + ssn + ']';
    }

    public Integer getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public Date getMeasurementTimestamp() {
        return measurementTimestamp;
    }

    public String getCsv() {
        return csv;
    }

    public String getSsn() {
        return ssn;
    }
}
