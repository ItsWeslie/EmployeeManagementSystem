package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EMPAttendanceDataIF {
    public ResponseEntity<?> getMyAttendanceDetails(@PathVariable("emp_id") String emp_id);
    public ResponseEntity<String> takingAttendance(@RequestBody AttendanceRequestDto attendanceRequestDto);
}
