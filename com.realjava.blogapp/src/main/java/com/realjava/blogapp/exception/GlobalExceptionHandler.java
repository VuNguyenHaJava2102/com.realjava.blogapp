package com.realjava.blogapp.exception;

import com.realjava.blogapp.payload.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest request) {
        CustomError error = new CustomError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<CustomError> handleInternalException(InternalException exception,
                                                               WebRequest request) {
        CustomError error = new CustomError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomError> handleAcc(AccessDeniedException exception,
                                                 WebRequest request) {
        CustomError error = new CustomError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleGlobalException(Exception exception,
                                                             WebRequest request) {
        CustomError error = new CustomError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle validation exception
    /*
    - When we validate field with springboot-validation and occur error, spring boot automatically throw
    MethodArgumentNotValidException. We can customize response error by 2 way
        + 1: Handle exception directly by @ExceptionHandler like below
        + 2: extends class: ResponseEntityExceptionHandler then override method: handleMethodArgumentNotValid
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /*
    // This is second way to handle validation exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
     */
}
