package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/getMyData")
    public ResponseEntity<?> getMyData(@RequestParam("email") String email) {

        return employeeService.getMyData(email);
    }

    @PutMapping("/updateMyData/{emp_id}")
    public ResponseEntity<?> updateMyData(@PathVariable("emp_id") String emp_id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.updateMyData(emp_id,employeeRequestDTO);
    }

    @GetMapping("/getNews")
    public ResponseEntity<?> getNews() {
        return employeeService.getNews();
    }

    @GetMapping("/getMyLeaveHistory/{emp_id}")
    public ResponseEntity<?> getMyLeaveHistory(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMyLeaveHistory(emp_id);
    }

    @PostMapping("/applyMyLeave")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeService.applyOrUpdateLeave(leaveRequestdto);
    }

    @PutMapping("/updateMyLeave")
    public ResponseEntity<String> updateLeaveRequest(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeService.applyOrUpdateLeave(leaveRequestdto);
    }

    @DeleteMapping("/deleteMyLeave/{leaveReq_id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("leaveReq_id") int id) {
        System.out.println(id);
        return employeeService.deleteLeaveRequest(id);
    }

    @GetMapping("/getMostUsedLeave/{emp_id}")
    public ResponseEntity<?> getMostUsedLeave(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMostUsedLeave(emp_id);
    }

}
