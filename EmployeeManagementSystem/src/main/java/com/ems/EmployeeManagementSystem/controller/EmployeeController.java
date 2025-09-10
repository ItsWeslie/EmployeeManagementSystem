package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import com.ems.EmployeeManagementSystem.model.AttendanceStatus;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/getMyData")
    public ResponseEntity<?> getMyData(@RequestParam("email") String email) {

        return employeeService.getMyData(email);
    }

    @PutMapping("/updateMyData/{id}")
    public ResponseEntity<?> updateMyData(@PathVariable("id") long id,
                                          @RequestPart("EmployeeRequestDTO") EmployeeRequestDTO employee,
                                          @RequestPart(value = "imageData",required = false) MultipartFile image
                                          ) {
        return employeeService.updateMyData(id,employee,image);
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

    //Attendence section

    @GetMapping("/getAttendance/{emp_id}")
    public ResponseEntity<AttendanceStatus> getAttendanceStatus(@PathVariable("emp_id") String emp_id) {
        //return employeeService.getAttendanceStatus(emp_id); // change this logic date 15-08-2025
        return ResponseEntity.ok(AttendanceStatus.PRESENT);
    }

    @GetMapping("/getMyAttendanceDetails/{emp_id}")
    public ResponseEntity<?> getMyAttendanceDetails(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMyAttendanceDetails(emp_id);
    }

    @PostMapping("/takingAttendance")
    public ResponseEntity<String> takingAttendance(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return employeeService.takingAttendance(attendanceRequestDto);
    }

    @GetMapping("/getCountOfPresent/{emp_id}")
    public ResponseEntity<?> getCountOfPresent(@PathVariable("emp_id") String emp_id) {
        return employeeService.getCountOfPresent(emp_id);
    }

    @GetMapping("/findLast7DaysAttendanceStatus/{emp_id}")
    public ResponseEntity<?> findLast7DaysAttendanceStatus(@PathVariable("emp_id") String emp_id) {
        return employeeService.findLast7DaysAttendanceStatus(emp_id);
    }

}
