package com.ems.EmployeeManagementSystem.exceptionHandling;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {

        super(message);
    }
}
