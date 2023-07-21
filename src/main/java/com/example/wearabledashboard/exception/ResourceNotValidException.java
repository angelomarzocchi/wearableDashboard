package com.example.wearabledashboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotValidException extends RuntimeException{

    public ResourceNotValidException(String resourceName, String fieldName,Object fieldValue){
        super(String.format("Resource %s with value [%s : %s] is not valid",resourceName,fieldName,fieldValue));

        this.errorMessage = String.format("Resource %s with value [%s : %s] is not valid",resourceName,fieldName,fieldValue);
    }
    public String getErrorMessage() {return errorMessage;}
    private final String errorMessage;
}
