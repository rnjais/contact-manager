package com.contact_manager.exception;

import com.contact_manager.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle contact not found
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ApiResponse> handleContactNotFound(
            ContactNotFoundException ex) {

        ApiResponse response = new ApiResponse(
                false,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationError(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiResponse response = new ApiResponse(
                false,
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Handle invalid page and size
    @ExceptionHandler(InvalidPageAndSizeNumber.class)
    public ResponseEntity<ApiResponse> handleInvalidPageAndSizeNumber(
            InvalidPageAndSizeNumber ex) {

        ApiResponse response = new ApiResponse(
                false,
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Handle invalid sorting field or direction
    @ExceptionHandler(InvalidSortingInput.class)
    public ResponseEntity<ApiResponse> handleInvalidSortingInput(
            InvalidSortingInput ex) {

        ApiResponse response = new ApiResponse(
                false,
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
    // Handle duplicate phone number or email
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDuplicateData(
            DataIntegrityViolationException ex) {

        ApiResponse response = new ApiResponse(
                false,
                "Phone number or email already exists",
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}