package com.ems.EmployeeManagementSystem.exceptionHandling;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException(String message) {
        super(message);
    }
}
