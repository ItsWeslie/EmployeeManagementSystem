package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.service.adminService.AdminEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminEmployeeController{

    private final AdminEmployeeService adminEmployeeService;

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return adminEmployeeService.getEmployees();
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return adminEmployeeService.addEmployee(employeeRequestDTO);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employee,
                                            @PathVariable("id") long id) {
        return adminEmployeeService.updateEmployee(id,employee);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        return adminEmployeeService.deleteEmployee(id);
    }
}
