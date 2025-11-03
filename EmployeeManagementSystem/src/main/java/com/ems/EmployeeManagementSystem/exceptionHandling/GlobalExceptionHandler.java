package com.ems.EmployeeManagementSystem.exceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<?> handleLeaveNotFoundException(LeaveNotFoundException ex) {
     ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),LocalDate.now());
     return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFound ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),LocalDate.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),LocalDate.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        ExceptionResponse error = new ExceptionResponse("Invalid email or password", LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeaveSummaryNotFoundException.class)
    public ResponseEntity<?> handleLeaveSummaryNotFoundException(LeaveSummaryNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<?> handleAttendanceNotFoundException(AttendanceNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SalaryNotFoundException.class)
    public ResponseEntity<?> handleSalaryNotFoundException(SalaryNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NextPaymentDateNotFoundException.class)
    public ResponseEntity<?> handleNextPaymentDateNotFoundException(NextPaymentDateNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SalaryInsightsNotFoundException.class)
    public ResponseEntity<?> handleSalaryInsightsNotFoundException(SalaryInsightsNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
