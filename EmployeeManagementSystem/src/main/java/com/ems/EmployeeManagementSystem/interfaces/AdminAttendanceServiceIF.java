package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminAttendanceServiceIF {
    ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords();
    ResponseEntity<?> updateAttendanceStatus(int attendanceId, AttendanceRequestDto attendanceRequestDto);
    ResponseEntity<String> deleteAttendance(int attendanceId);
}
