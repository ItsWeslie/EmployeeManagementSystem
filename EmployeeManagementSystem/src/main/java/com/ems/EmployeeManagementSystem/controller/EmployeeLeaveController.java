package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeLeaveController {

    private final EmployeeLeaveService employeeLeaveService;

    @GetMapping("/getMyLeaveHistory/{emp_id}")
    public ResponseEntity<?> getMyLeaveHistory(@PathVariable("emp_id") String emp_id) {
        return employeeLeaveService.getMyLeaveHistory(emp_id);
    }

    @PostMapping("/applyLeave")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeLeaveService.applyOrUpdateLeave(leaveRequestdto);
    }

    @PutMapping("/updateMyLeave")
    public ResponseEntity<String> updateLeaveRequest(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeLeaveService.applyOrUpdateLeave(leaveRequestdto);
    }

    @DeleteMapping("/deleteMyLeave/{leaveReq_id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("leaveReq_id") int id) {
        return employeeLeaveService.deleteLeaveRequest(id);
    }
}
