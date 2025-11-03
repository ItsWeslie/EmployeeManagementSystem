package com.ems.EmployeeManagementSystem.exceptionHandling;

public class SalaryNotFoundException extends RuntimeException {

    public SalaryNotFoundException(String message) {
        super(message);
    }
}
