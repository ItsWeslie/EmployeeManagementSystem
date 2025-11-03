package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.enums.LeaveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveResponseDTO {
    private String empId;
    private String leaveReason;
    private int leaveId;
    private String name;
    private String department;

    private LeaveStatus leaveStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
