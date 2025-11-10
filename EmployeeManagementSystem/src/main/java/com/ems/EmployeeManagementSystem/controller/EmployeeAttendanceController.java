package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeAttendanceController {

    private final EmployeeAttendanceService employeeAttendanceService;

    @GetMapping("/getMyAttendanceDetails/{emp_id}")
    public ResponseEntity<?> getMyAttendanceDetails(@PathVariable("emp_id") String emp_id) {
        return employeeAttendanceService.getMyAttendanceDetails(emp_id);
    }

    @PostMapping("/takeAttendance")
    public ResponseEntity<String> takingAttendance(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return employeeAttendanceService.takingAttendance(attendanceRequestDto);
    }
}
