package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.LeaveResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminLeaveServiceIF {
    ResponseEntity<List<LeaveResponseDTO>> getLeaveRecords();
    ResponseEntity<String> approveLeave(int leaveRequestId);
    ResponseEntity<String> rejectLeave(int id);
}
