package com.ems.EmployeeManagementSystem.controller.controllerIF;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EmployeeControllerIF {
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees();
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO);
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employee,@PathVariable("id") long id);
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id);
}
