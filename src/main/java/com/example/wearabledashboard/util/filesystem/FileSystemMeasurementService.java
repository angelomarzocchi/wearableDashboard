package com.example.wearabledashboard.util.filesystem;

import com.example.wearabledashboard.dto.MeasurementDTO;

import java.io.IOException;

public interface FileSystemMeasurementService {

    /**
     * crates a new csv file to store the measurement's data
      * @param measurementDTO the measurement to be stored
     * @return the String representing the path
     */
     String createFile(MeasurementDTO measurementDTO) throws IOException;

    /**
     * reads the file
     * @param measurementDTO the measurement's csv to be red
     * @return returns a stringyfied csv linked to measurementDTO
     */
     String readFile(MeasurementDTO measurementDTO) throws IOException;
}
