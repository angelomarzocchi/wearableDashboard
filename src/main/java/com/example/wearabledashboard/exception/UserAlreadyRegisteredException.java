package com.example.wearabledashboard.exception;

public class UserAlreadyRegisteredException extends ResourceNotValidException{
    public UserAlreadyRegisteredException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName, fieldName, fieldValue);
    }
}
