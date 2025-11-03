package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDashDTO {

 private AttendanceStatus attendanceStatus;
 private List<Attendance> overAllAttendance;
 private List<Last7DaysAttendanceDTO> last7DaysAttendance;
 private int totalPresentInCurrentMonth;
 private int totalDaysInCurrentMonth;
 private int totalWorkingDays;
 private int leaveTaken;
 private double monthlyAttendancePercentage;
 private double totalAttendancePercentage;

}
