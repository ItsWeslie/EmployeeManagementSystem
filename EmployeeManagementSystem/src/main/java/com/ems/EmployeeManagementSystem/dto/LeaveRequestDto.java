package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {

    private String empId;
    private LeaveType leaveType;
    private String leaveReason;
    private int leaveId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
