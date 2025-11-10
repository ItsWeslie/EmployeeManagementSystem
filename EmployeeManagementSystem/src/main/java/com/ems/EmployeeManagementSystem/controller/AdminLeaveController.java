package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.LeaveResponseDTO;
import com.ems.EmployeeManagementSystem.service.adminService.AdminLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLeaveController{

    private final AdminLeaveService adminLeaveService;

    @GetMapping("/getLeaveRecords")
    public ResponseEntity<List<LeaveResponseDTO>> getPendingLeaves() {
        return adminLeaveService.getLeaveRecords();
    }

    @PatchMapping("/approveLeave/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable("id") int id) {
        return adminLeaveService.approveLeave(id);
    }

    @PatchMapping("/rejectLeave/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable("id") int id) {
        return adminLeaveService.rejectLeave(id);
    }
}
