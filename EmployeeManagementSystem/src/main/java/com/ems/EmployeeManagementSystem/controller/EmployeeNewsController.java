package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.service.adminService.AdminNewsService;
import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeNewsController {

    private final AdminNewsService adminNewsService;
    private final EmployeeNewsService employeeNewsService;

    @GetMapping("/getNews/{empId}")
    public ResponseEntity<List<NewsRespDTO>> getNews(@PathVariable("empId") String empId) {
        return adminNewsService.getNews(empId);
    }

    @PutMapping("/markNewsAsRead")
    public void markNewsAsRead(@RequestBody NewsReqDTO newsReqDTO) {
        employeeNewsService.markAsRead(newsReqDTO.getEmpId(),newsReqDTO.getNewsId());
    }

    @PutMapping("/markAllNewsAsRead/{empId}")
    public void markAllNewsAsRead(@PathVariable("empId") String empId) {
        employeeNewsService.markAllAsRead(empId);
    }

}
