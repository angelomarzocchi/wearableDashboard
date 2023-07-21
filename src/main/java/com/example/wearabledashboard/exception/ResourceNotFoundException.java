package com.example.wearabledashboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(String.format("Resource %s with value [%s : %s] not found",resourceName,fieldName,fieldValue));
    }
}
