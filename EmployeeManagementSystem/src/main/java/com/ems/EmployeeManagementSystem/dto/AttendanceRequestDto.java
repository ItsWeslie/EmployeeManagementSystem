package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDto {

    private int attendanceId;

    private String empId;

    private AttendanceStatus attendanceStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate date;

    private LocalTime time;

    private int month;

    private Year year;

}
