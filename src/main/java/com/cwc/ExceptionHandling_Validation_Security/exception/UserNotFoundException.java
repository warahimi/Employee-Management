package com.cwc.ExceptionHandling_Validation_Security.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
