package com.example.wearabledashboard.util.filesystem;

import com.example.wearabledashboard.dto.MeasurementDTO;
import com.example.wearabledashboard.exception.ResourceNotValidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileSystemMeasurementWindowsImpl implements FileSystemMeasurementService {

    private static final String MEASUREMENT_DTO = "MeasurementDTO";


    private static final String CSV_EXT = ".csv";

    @Value("${measurements.folder}")
    private String measurementBasePath;


    @Override
    public String createFile(MeasurementDTO measurementDTO) throws IOException {
        if(measurementDTO == null)
            throw new ResourceNotValidException(MEASUREMENT_DTO,"measurementDTO","null");
        if(measurementDTO.getCsv() ==null)
            throw new ResourceNotValidException(MEASUREMENT_DTO,"csv","null");

        File directory = new File(measurementBasePath);
        if(!directory.exists())
            directory.mkdirs();

        byte[] measurementContent = measurementDTO.getCsv().getBytes();

        String measurementRelativePath = String.format(
                "%s\\%s%s%s",
                measurementDTO.getSsn(),
                measurementDTO.getMeasurementTimestamp().toInstant().toString().replace(":",""),
                measurementDTO.getDevice().toString().toUpperCase(),
                CSV_EXT);
        File file = new File(directory,measurementRelativePath);



        if(!file.exists())
            file.createNewFile();



        Files.write(file.toPath(),measurementContent);
        return file.toPath().toString();

    }

    @Override
    public String readFile(MeasurementDTO measurementDTO) throws IOException {
        if(measurementDTO == null)
            throw new ResourceNotValidException(MEASUREMENT_DTO,MEASUREMENT_DTO,"null");
        if(measurementDTO.getCsv() ==null)
            throw new ResourceNotValidException(MEASUREMENT_DTO,"csv","null");

        File directory = new File(measurementBasePath);
        if(!directory.exists())
            throw new ResourceNotValidException("FileSystemMeasurementWindowsImpl","measurementBasePath","no measurements saved yet");

        String measurementRelativePath = String.format(
                "%s\\%s%s%s",
                measurementDTO.getSsn(),
                measurementDTO.getMeasurementTimestamp().toInstant().toString().replace(":",""),
                measurementDTO.getDevice().toString().toUpperCase(),
                CSV_EXT);

        File file = new File(directory,measurementRelativePath);

        if(!file.exists())
            throw new ResourceNotValidException(MEASUREMENT_DTO,"csv",measurementDTO.getCsv());

        byte[] csvContent = Files.readAllBytes(file.toPath());

        return new String(csvContent);


    }
}



