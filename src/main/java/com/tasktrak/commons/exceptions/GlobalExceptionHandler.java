package com.tasktrak.commons.exceptions;

import com.tasktrak.commons.responses.ErrorResponseFormat;
import com.tasktrak.commons.responses.ErrorResponseSimpleFormat;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//@RestControllerAdvice

public class GlobalExceptionHandler {


    private ErrorResponseFormat errorResponse;

    private ErrorResponseSimpleFormat errorSimpleResponse;

    public GlobalExceptionHandler(ErrorResponseFormat errorResponse, ErrorResponseSimpleFormat errorSimpleResponse) {
        this.errorResponse = errorResponse;
        this.errorSimpleResponse = errorSimpleResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseFormat> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, List<String>> errorDetails = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setMessage("Validation Failed");
        errorResponse.setDetails(errorDetails);
        errorResponse.setPath(request.getRequestURI());

        return ResponseEntity.badRequest().body(errorResponse);
    }



    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseSimpleFormat> handleNoSuchElementException(
            NoSuchElementException exception, HttpServletRequest request) {

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Resource Not Found");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimpleResponse);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseSimpleFormat> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception, HttpServletRequest request) {

        errorSimpleResponse.setStatus("400");
        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Data Integrity Violation");
        errorSimpleResponse.setDetails(Arrays.asList(exception.getMessage().split(";")));
        errorSimpleResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseSimpleFormat> handleIllegalArgumentException(
            IllegalArgumentException exception, HttpServletRequest request) {

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Illegal Argument Exception");
        errorSimpleResponse.setDetails(Arrays.asList(exception.getMessage().split(";")));
        errorSimpleResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleDatabaseConnectionException(ConnectException ex) {
        // Log the exception or print a custom message
        System.out.println("Error: Unable to connect to the database. Please make sure your Laragon server is running.");

        // You can customize the response message and HTTP status based on your requirements
        return new ResponseEntity<>("Unable to connect to the database.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}