package com.ems.EmployeeManagementSystem.exceptionHandling;

public class SalaryAlreadyExistException extends RuntimeException {
    public SalaryAlreadyExistException(String message) {
        super(message);
    }
}
