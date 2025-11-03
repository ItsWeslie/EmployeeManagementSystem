package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.controller.controllerIF.EMPAttendanceDataIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.EMPLeaveDataIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.EMPDataIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.EMPNewsDataIF;
import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.ems.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EmployeeController implements EMPDataIF, EMPLeaveDataIF, EMPAttendanceDataIF, EMPNewsDataIF {

    private final EmployeeService employeeService;

    //emp data section
    @GetMapping("/getMyData/{email}")
    public ResponseEntity<?> getMyData(@PathVariable("email") String email) {
        return employeeService.getMyData(email);
    }

    @PutMapping("/updateMyProfilePic/{empId}")
    public ResponseEntity<?> updateMyData(@PathVariable("empId") String empId,
                                          @RequestPart(value = "imageData",required = false) MultipartFile image
                                          ) {
        return employeeService.updateMyData(empId,image);
    }

    @PutMapping("/changeMyPassword")
    public ResponseEntity<?> changeMyPassword(@Valid @RequestBody PasswordChangeReqDTO passwordChangeReqDTO) {
        System.out.println("inside changeMyPassword controller");
        return employeeService.changeMyPassword(passwordChangeReqDTO);
    }

    //Attendance section

    @GetMapping("/getMyAttendanceDetails/{emp_id}")
    public ResponseEntity<?> getMyAttendanceDetails(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMyAttendanceDetails(emp_id);
    }

    @PostMapping("/takeAttendance")
    public ResponseEntity<String> takingAttendance(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        return employeeService.takingAttendance(attendanceRequestDto);
    }

    //Leave section

    @GetMapping("/getMyLeaveHistory/{emp_id}")
    public ResponseEntity<?> getMyLeaveHistory(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMyLeaveHistory(emp_id);
    }

    @PostMapping("/applyLeave")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeService.applyOrUpdateLeave(leaveRequestdto);
    }

    @PutMapping("/updateMyLeave")
    public ResponseEntity<String> updateLeaveRequest(@RequestBody LeaveRequestDto leaveRequestdto) {
        return employeeService.applyOrUpdateLeave(leaveRequestdto);
    }

    @DeleteMapping("/deleteMyLeave/{leaveReq_id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("leaveReq_id") int id) {
        return employeeService.deleteLeaveRequest(id);
    }

    //salary section
    @GetMapping("/getMySalaryDetails/{emp_id}")
    public ResponseEntity<?> getMySalaryDetails(@PathVariable("emp_id") String emp_id) {
        return employeeService.getMySalaryDetails(emp_id);
    }

    //news section
    @GetMapping("/getNews/{empId}")
    public ResponseEntity<List<NewsRespDTO>> getNews(@PathVariable("empId") String empId) {
        return employeeService.getNews(empId);
    }

    @PutMapping("/markNewsAsRead")
    public void markNewsAsRead(@RequestBody NewsReqDTO newsReqDTO) {
        employeeService.markNewsAsRead(newsReqDTO);
    }

    @PutMapping("/markAllNewsAsRead/{empId}")
    public void markAllNewsAsRead(@PathVariable("empId") String empId) {
        employeeService.markAllNewsAsRead(empId);
    }

}
