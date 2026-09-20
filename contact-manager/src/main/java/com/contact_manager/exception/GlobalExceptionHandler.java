package com.contact_manager.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Handle Contact not found
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<String> handleContactNotFound(ContactNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    // handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

              ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField() ,error.getDefaultMessage())
                );
              return ResponseEntity.badRequest().body(errors);
    }
}
