package com.ems.EmployeeManagementSystem.service.servicesIF;

import com.ems.EmployeeManagementSystem.dto.AdminSalaryResponseDTO;
import com.ems.EmployeeManagementSystem.dto.SalaryRequestDTO;
import com.ems.EmployeeManagementSystem.model.Salary;
import org.springframework.http.ResponseEntity;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface AdminSalaryIF {
    ResponseEntity<Salary> addSalary(SalaryRequestDTO salaryRequestDTO);
    ResponseEntity<String> updateSalary(SalaryRequestDTO salaryRequestDTO);
    ResponseEntity<String> deleteSalary(int salaryID);
    ResponseEntity<AdminSalaryResponseDTO> getSalaryDash();
    ResponseEntity<String> approveSalary(int salaryId);
}
