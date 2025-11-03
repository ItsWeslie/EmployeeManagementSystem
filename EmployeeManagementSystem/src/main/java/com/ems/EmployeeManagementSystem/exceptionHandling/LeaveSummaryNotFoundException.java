package com.ems.EmployeeManagementSystem.exceptionHandling;

public class LeaveSummaryNotFoundException extends RuntimeException{

    public LeaveSummaryNotFoundException(String message) {
        super(message);
    }
}
