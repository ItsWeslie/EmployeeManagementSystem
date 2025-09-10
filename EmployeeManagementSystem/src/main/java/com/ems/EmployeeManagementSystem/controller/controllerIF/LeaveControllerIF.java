package com.ems.EmployeeManagementSystem.controller.controllerIF;

import com.ems.EmployeeManagementSystem.dto.LeaveResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LeaveControllerIF {
    public ResponseEntity<List<LeaveResponseDTO>> getPendingLeaves();
    public ResponseEntity<String> approveLeave(@PathVariable("id") int id);
    public ResponseEntity<String> rejectLeave(@PathVariable("id") int id);
}
