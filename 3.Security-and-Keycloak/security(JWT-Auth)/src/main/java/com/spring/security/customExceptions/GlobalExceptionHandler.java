package com.spring.security.customExceptions;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, RoleNotFoundException.class, AuthorityNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", e.getMessage());
        response.put("statusCode", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", "Duplicate entry detected");
        response.put("statusCode", String.valueOf(HttpStatus.CONFLICT.value()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> messageTemplates = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toList());
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", String.join(", ", messageTemplates));
        response.put("statusCode", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle general runtime exceptions (e.g., database issues, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", e.getMessage());
        response.put("statusCode", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(IllegalArgumentException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Bad Request");
        response.put("message", e.getMessage());
        response.put("statusCode", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // Handle un-authorized requests
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Token is expired");
        response.put("message", "The JWT token has expired. Please log in again.");
        response.put("expiration_time", e.getClaims().getExpiration().toString());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
