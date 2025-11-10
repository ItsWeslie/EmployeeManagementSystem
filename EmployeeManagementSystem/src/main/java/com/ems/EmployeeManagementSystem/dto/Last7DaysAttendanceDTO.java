package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Last7DaysAttendanceDTO {
    private AttendanceStatus attendanceStatus;
    private String date;
}
