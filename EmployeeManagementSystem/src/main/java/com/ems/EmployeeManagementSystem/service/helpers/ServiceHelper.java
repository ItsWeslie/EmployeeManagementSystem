package com.ems.EmployeeManagementSystem.service.helpers;

import com.ems.EmployeeManagementSystem.exceptionHandling.ResourceNotFound;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final EmployeeRepo employeeRepo;

    public Employee isValidEmployee(String empId) {
        return employeeRepo.findByEmpId(empId).orElseThrow(() -> new ResourceNotFound("Employee Not Found"));
    }

    public boolean isEmployeeExist(String empId) {
        return employeeRepo.findByEmpId(empId).isPresent();
    }

}
