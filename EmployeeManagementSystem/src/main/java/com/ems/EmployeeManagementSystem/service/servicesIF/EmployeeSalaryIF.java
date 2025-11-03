package com.ems.EmployeeManagementSystem.service.servicesIF;

import com.ems.EmployeeManagementSystem.dto.EmployeeSalaryResponseDTO;
import org.springframework.http.ResponseEntity;

public interface EmployeeSalaryIF {
    public ResponseEntity<EmployeeSalaryResponseDTO> getEmployeeSalaryDetails(String empId);
}
