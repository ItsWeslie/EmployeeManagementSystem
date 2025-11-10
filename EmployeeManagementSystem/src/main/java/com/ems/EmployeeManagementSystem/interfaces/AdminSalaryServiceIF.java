package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.AdminSalaryResponseDTO;
import com.ems.EmployeeManagementSystem.dto.SalaryRequestDTO;
import com.ems.EmployeeManagementSystem.model.Salary;
import org.springframework.http.ResponseEntity;

public interface AdminSalaryServiceIF {
    ResponseEntity<Salary> addSalary(SalaryRequestDTO salaryRequestDTO);
    ResponseEntity<String> updateSalary(SalaryRequestDTO salaryRequestDTO);
    ResponseEntity<String> deleteSalary(int salaryID);
    ResponseEntity<AdminSalaryResponseDTO> getSalaryDash();
    ResponseEntity<String> approveSalary(int salaryId);
}
