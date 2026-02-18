package com.ems.EmployeeManagementSystem.mappers;

import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import com.ems.EmployeeManagementSystem.model.Attendance;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AttendanceMapper implements Function<Attendance, AttendanceResponseDTO> {
    @Override
    public AttendanceResponseDTO apply(Attendance attendance) {
        return AttendanceResponseDTO.builder()
                .attendanceId(attendance.getId())
                .attendanceStatus(attendance.getStatus())
                .date(attendance.getDate())
                .time(attendance.getTime())
                .month(attendance.getMonth())
                .year(attendance.getYear())
                .empId(attendance.getEmployee().getEmpId())
                .name(attendance.getEmployee().getName())
                .department(attendance.getEmployee().getDepartment())
                .build();
    }
}
