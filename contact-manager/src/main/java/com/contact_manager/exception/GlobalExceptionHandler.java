package com.contact_manager.exception;

import com.contact_manager.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> handleContactNotFound(ContactNotFoundException ex){
        ApiResponse response = new ApiResponse(
                false,
                ex.getMessage(),
                null
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    // handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

              ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField() ,error.getDefaultMessage())
                );
        ApiResponse response = new ApiResponse(
                false,
                "Validation failed",
                 errors
        );
              return ResponseEntity.badRequest().body(response);
    }
}
