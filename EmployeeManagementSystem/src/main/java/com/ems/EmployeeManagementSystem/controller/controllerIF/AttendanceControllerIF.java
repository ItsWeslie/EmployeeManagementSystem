package com.ems.EmployeeManagementSystem.controller.controllerIF;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AttendanceControllerIF {

    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords();
    public ResponseEntity<?> updateAttendanceStatus(
            @PathVariable("attendance_id") int attendance_id,
            @RequestBody AttendanceRequestDto attendanceRequestDto);
    public ResponseEntity<String> deleteAttendance(@PathVariable("attendance_id") int attendance_id);
}
