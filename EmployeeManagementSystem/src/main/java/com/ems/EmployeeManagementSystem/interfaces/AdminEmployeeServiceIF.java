package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.model.Employee;
import org.springframework.http.ResponseEntity;

public interface AdminEmployeeServiceIF {
    ResponseEntity<Employee> addEmployee(EmployeeRequestDTO employeeRequestDTO);
    ResponseEntity<?> updateEmployee(long id, EmployeeRequestDTO employee);
    ResponseEntity<String> deleteEmployee(long id);
}
