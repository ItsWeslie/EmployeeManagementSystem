package com.ems.EmployeeManagementSystem.exceptionHandling;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
