package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.AdminSalaryResponseDTO;
import com.ems.EmployeeManagementSystem.dto.SalaryRequestDTO;
import com.ems.EmployeeManagementSystem.model.Salary;
import com.ems.EmployeeManagementSystem.service.adminService.AdminSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminSalaryController {

    private final AdminSalaryService adminSalaryService;

    @PostMapping("/addSalary")
    public ResponseEntity<Salary> addSalary(@RequestBody SalaryRequestDTO salaryRequestDTO) {
        return adminSalaryService.addSalary(salaryRequestDTO);
    }

    @PatchMapping("/approveSalary/{salary_id}")
    public ResponseEntity<String> approveSalary(@PathVariable("salary_id") int salary_id) {
        return adminSalaryService.approveSalary(salary_id);
    }

    @PutMapping("/updateSalary")
    public ResponseEntity<String> updateSalary(@RequestBody SalaryRequestDTO salaryRequestDTO) {
        return adminSalaryService.updateSalary(salaryRequestDTO);
    }

    @DeleteMapping("/deleteSalary/{salary_id}")
    public ResponseEntity<String> deleteSalary(@PathVariable("salary_id") int salary_id) {
        return adminSalaryService.deleteSalary(salary_id);
    }

    @GetMapping("/getSalaryDash")
    public ResponseEntity<AdminSalaryResponseDTO> getSalaries() {
        return adminSalaryService.getSalaryDash();
    }
}
