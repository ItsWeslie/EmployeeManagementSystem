package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.EmployeeSalaryResponseDTO;
import org.springframework.http.ResponseEntity;

public interface EmployeeSalaryServiceIF {
    public ResponseEntity<EmployeeSalaryResponseDTO> getMySalaryDetails(String empId);
}
