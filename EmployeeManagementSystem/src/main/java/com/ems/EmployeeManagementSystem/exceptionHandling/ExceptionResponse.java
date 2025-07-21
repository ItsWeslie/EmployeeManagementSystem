package com.ems.EmployeeManagementSystem.exceptionHandling;

import org.springframework.stereotype.Component;

import java.time.LocalDate;


public class ExceptionResponse {

    private String message;
    private LocalDate timestamp;

    public ExceptionResponse(String message, LocalDate timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
