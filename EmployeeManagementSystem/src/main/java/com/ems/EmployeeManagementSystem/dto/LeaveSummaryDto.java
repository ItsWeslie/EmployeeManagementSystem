package com.ems.EmployeeManagementSystem.dto;


import com.ems.EmployeeManagementSystem.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSummaryDto {


    private int id;

    private String empId;

    private int remainingLeave;

    private int leaveTaken;

    private int totalLeave;

    private int totalLeavesAllowed;

    private long longestLeaveDuration;

    private int year;

    private int month;

    private Employee employee;

}
