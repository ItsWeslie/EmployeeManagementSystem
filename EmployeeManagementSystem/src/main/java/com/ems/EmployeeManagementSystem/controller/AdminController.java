package com.ems.EmployeeManagementSystem.controller;


import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import com.ems.EmployeeManagementSystem.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final AdminService adminService;


    //Employee controller section

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {

        List<EmployeeResponseDTO> employees = adminService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@Valid @RequestPart("EmployeeRequestDTO") EmployeeRequestDTO employee,@RequestPart(value = "imageData",required = false) MultipartFile image) {

        try {
            return adminService.addEmployee(employee, image);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateEmployee/{emp_id}")
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employee,@PathVariable("emp_id") String empId) {
            return adminService.updateEmployee(empId,employee);
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
    public ResponseEntity<String> addNews(@RequestBody News news) {

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

    //leave controller section

    @GetMapping("/getPendingLeaves")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaves() {

        return adminService.getPendingLeaves();
    }

    @GetMapping("/getApprovedLeaves")
    public ResponseEntity<List<LeaveRequest>> getApprovedLeaves() {

        return adminService.getApprovedLeaves();
    }

    @GetMapping("/getRejectedLeaves")
    public ResponseEntity<List<LeaveRequest>> getRejectedLeaves() {
        return adminService.getRejectedLeaves();
    }

    @PostMapping("/approveLeave/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable("id") int id) {

        return adminService.approveLeave(id);
    }

    @PatchMapping("/rejectLeave/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable("id") int id) {
        return adminService.rejectLeave(id);
    }

}
