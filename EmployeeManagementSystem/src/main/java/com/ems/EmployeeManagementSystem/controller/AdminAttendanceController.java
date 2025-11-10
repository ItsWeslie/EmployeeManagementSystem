package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import com.ems.EmployeeManagementSystem.service.adminService.AdminAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAttendanceController{

    private final AdminAttendanceService adminAttendanceService;

    @GetMapping("/getAttendanceRecords")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords() {
        return adminAttendanceService.getAttendanceRecords();
    }

    @PatchMapping("/updateAttendanceStatus/{attendance_id}")
    public ResponseEntity<?> updateAttendanceStatus(
            @PathVariable("attendance_id") int attendance_id,
            @RequestBody AttendanceRequestDto attendanceRequestDto) {
        return adminAttendanceService.updateAttendanceStatus(attendance_id,attendanceRequestDto);
    }

    @DeleteMapping("/deleteAttendance/{attendance_id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable("attendance_id") int attendance_id) {
        return adminAttendanceService.deleteAttendance(attendance_id);
    }
}
