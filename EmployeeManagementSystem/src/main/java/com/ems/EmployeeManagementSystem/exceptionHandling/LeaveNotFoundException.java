package com.ems.EmployeeManagementSystem.exceptionHandling;


public class LeaveNotFoundException extends RuntimeException{

    public LeaveNotFoundException(String message) {
        super(message);
    }
}
