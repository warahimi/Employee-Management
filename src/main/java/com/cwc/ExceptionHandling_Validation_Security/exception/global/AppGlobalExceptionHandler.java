package com.cwc.ExceptionHandling_Validation_Security.exception.global;

import com.cwc.ExceptionHandling_Validation_Security.exception.EmployeesNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotCreatedException;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppGlobalExceptionHandler {
    @ExceptionHandler(EmployeesNotFoundException.class)
    public ResponseEntity<ErrorMessage>handleEmployeesNotFoundException(EmployeesNotFoundException e)
    {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.NOT_FOUND.toString())
                .error("Employee Not Found")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String errorField = error.getField();
            String errorFieldMessage = error.getDefaultMessage();
            errorMap.put(errorField, errorFieldMessage);
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, String> errorMap = new HashMap<>();

        Throwable rootCause = e.getMostSpecificCause();

        // Check if the cause of the exception is related to date parsing
        if (rootCause instanceof java.time.format.DateTimeParseException) {
            errorMap.put("date", "Invalid date format. Please use the format MM/dd/yyyy.");
        } else {
            // General message if it's a different type of parsing error
            errorMap.put("error", "Invalid request body. Please check your input.");
        }

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorMessage> handleHandlerMethodValidationException(HandlerMethodValidationException e)
    {
        ErrorMessage invalidParameter = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error("Invalid Parameter: HandlerMethodValidationException")
                .message(e.getMessage())
                .build();
        return  new ResponseEntity<>(invalidParameter, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e)
    {
        ErrorMessage invalidParameter = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error("Invalid Parameter:  IllegalArgumentException")
                .message(e.getMessage())
                .build();
        return  new ResponseEntity<>(invalidParameter, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ErrorMessage> handleUserNotCreatedException(UserNotCreatedException ex) {
        ErrorMessage userNotCreated = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .error("User Not Created")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(userNotCreated, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException e)
    {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.NOT_FOUND.toString())
                .error("User Not Found")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e)
    {
        ErrorMessage exceptionOccured = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .message(e.getMessage())
                .error("Exception Occured")
                .status(HttpStatus.BAD_REQUEST.toString())
                .build();
        return new ResponseEntity<>(exceptionOccured, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extract the root cause (for example, constraint violations)
        Throwable rootCause = ex.getRootCause();
        String message = (rootCause != null) ? rootCause.getMessage() : ex.getMessage();

        ErrorMessage errorMessage = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.CONFLICT.toString())  // HTTP 409 Conflict for duplicate entries
                .error("Data Integrity Violation")
                .message("Duplicate entry detected: " + message)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedRollbackException(UnexpectedRollbackException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .error("Transaction Rollback")
                .message("Transaction was rolled back due to: " + ex.getMostSpecificCause().getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        ErrorMessage usernameNotFound = ErrorMessage.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .status(HttpStatus.NOT_FOUND.toString())
                .error("Username Not Found")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(usernameNotFound, HttpStatus.NOT_FOUND);
    }

}
