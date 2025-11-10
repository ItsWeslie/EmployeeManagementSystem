package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeSalaryController {
    private final EmployeeSalaryService employeeSalaryService;

    @GetMapping("/getMySalaryDetails/{emp_id}")
    public ResponseEntity<?> getMySalaryDetails(@PathVariable("emp_id") String emp_id) {
        return employeeSalaryService.getMySalaryDetails(emp_id);
    }
}
