package com.ems.EmployeeManagementSystem.controller;


import com.ems.EmployeeManagementSystem.controller.controllerIF.AttendanceControllerIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.EmployeeControllerIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.LeaveControllerIF;
import com.ems.EmployeeManagementSystem.controller.controllerIF.NewsControllerIF;
import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AdminController implements EmployeeControllerIF, AttendanceControllerIF
        , LeaveControllerIF, NewsControllerIF {

    private final AdminService adminService;

    //Employee controller section

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return adminService.getEmployees();
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
            return adminService.addEmployee(employeeRequestDTO);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employee,@PathVariable("id") long id) {
            return adminService.updateEmployee(id,employee);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        return adminService.deleteEmployee(id);
    }

    //News Controller Section
    @GetMapping("/getNews")
    public ResponseEntity<List<News>> getNews() {
        return adminService.getNews();
    }

    @PostMapping("/addNews")
    public ResponseEntity<?> addNews(@RequestBody News news) {
        return adminService.addNews(news);
    }

    @PutMapping("/updateNews/{newsId}")
    public ResponseEntity<String> updateNews(@RequestBody News news,@PathVariable("newsId") int newsId) {
        return adminService.updateNews(newsId,news);
    }

    @DeleteMapping("/deleteNews/{newsId}")
    public ResponseEntity<String> deleteNews(@PathVariable("newsId") int newsId) {
        return adminService.deleteNews(newsId);
    }

    //attendance controller section

    @GetMapping("/getAttendanceRecords")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords() {
        return adminService.getAttendanceRecords();
    }

    @PatchMapping("/updateAttendanceStatus/{attendance_id}")
    public ResponseEntity<?> updateAttendanceStatus(
            @PathVariable("attendance_id") int attendance_id,
            @RequestBody AttendanceRequestDto attendanceRequestDto) {
        return adminService.updateAttendanceStatus(attendance_id,attendanceRequestDto);
    }

    @DeleteMapping("/deleteAttendance/{attendance_id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable("attendance_id") int attendance_id) {
        return adminService.deleteAttendance(attendance_id);
    }

    //leave controller section

    @GetMapping("/getLeaveRecords")
    public ResponseEntity<List<LeaveResponseDTO>> getPendingLeaves() {
        return adminService.getLeaveRecords();
    }

    @PatchMapping("/approveLeave/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable("id") int id) {
        return adminService.approveLeave(id);
    }

    @PatchMapping("/rejectLeave/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable("id") int id) {
        return adminService.rejectLeave(id);
    }

    //Salary section

    @PostMapping("/addSalary")
    public ResponseEntity<String> addSalary(@RequestBody SalaryRequestDTO salaryRequestDTO) {
        return adminService.addSalary(salaryRequestDTO);
    }
}
