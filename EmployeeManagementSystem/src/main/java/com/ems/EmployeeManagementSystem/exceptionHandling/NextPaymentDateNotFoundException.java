package com.ems.EmployeeManagementSystem.exceptionHandling;

public class NextPaymentDateNotFoundException extends RuntimeException {
    public NextPaymentDateNotFoundException(String message) { super(message); }
}
