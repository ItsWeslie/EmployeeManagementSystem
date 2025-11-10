package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDataDTO {

    private String empId;
    private int remainingLeave;
    private int totalLeave;
    private int leaveTaken;
    private int longestLeave;
    private String mostTakenLeave;
    private Map<LeaveType,Integer> leavePieChart;
    private List<LeaveRequest> leaveRequests;

}
