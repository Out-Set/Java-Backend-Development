package com.savan.quartz.exceptionhandler;

import com.savan.quartz.dto.ResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(Exception e) {
    	ResponseDto response = new ResponseDto(
    		    e.getClass().getSimpleName(),
    		    e.getMessage(),
    		    HttpStatus.NOT_FOUND.value()
    		);
    	return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> messageTemplates = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toList());
        ResponseDto response = new ResponseDto(
        	    e.getClass().getSimpleName(),
        	    String.join(", ", messageTemplates),
        	    HttpStatus.BAD_REQUEST.value()
        	);
        return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(response);
    }

    // Handle general runtime exceptions (e.g., database issues, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception e) {
    	ResponseDto response = new ResponseDto(
        	    e.getClass().getSimpleName(),
        	    e.getMessage(),
        	    HttpStatus.INTERNAL_SERVER_ERROR.value()
        	);
    	return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(response);
    }

    @ExceptionHandler({IllegalArgumentException.class, BadRequestException.class})
    public ResponseEntity<ResponseDto> handleBadRequestException(Exception e) {
    	ResponseDto response = new ResponseDto(
        	    e.getClass().getSimpleName(),
        	    e.getMessage(),
        	    HttpStatus.BAD_REQUEST.value()
        	);
    	return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(response);
    }
    
    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<ResponseDto> handleUnexpectedRollbackException(UnexpectedRollbackException e) {
        Throwable rootCause = getRootCause(e);
        ResponseDto response = new ResponseDto();
        response.setError("UnexpectedRollbackException");
        if (rootCause instanceof DataIntegrityViolationException) {
            response.setMessage("Duplicate entry or data integrity violation: " + rootCause.getMessage());
            response.setStatusCode(HttpStatus.CONFLICT.value());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        response.setMessage(rootCause != null ? rootCause.getMessage() : e.getMessage());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(response);
    }

    // Utility to dig into root cause
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

}
