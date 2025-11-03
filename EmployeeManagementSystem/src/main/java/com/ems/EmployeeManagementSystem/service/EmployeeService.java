package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.repository.*;
import com.ems.EmployeeManagementSystem.service.servicehandlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final AttendanceRepo attendanceRepo;
    private final EmployeeDataHandler empDataHandlerService;
    private final NewsServiceHandler newsServiceHandler;
    private final LeaveRequestHandler leaveRequestHandler;
    private final AttendanceRequestHandler attendanceRequestHandler;
    private final NewsService newsService;
    private final SalaryServiceHandler salaryServiceHandler;


    // user data section
    public ResponseEntity<?> getMyData(String email) {
        return empDataHandlerService.getMyData(email);
    }

    public ResponseEntity<?> updateMyData(String empId,
                                          MultipartFile image) {
        return empDataHandlerService.updateMyData(empId, image);
    }

    public ResponseEntity<?> changeMyPassword(PasswordChangeReqDTO passwordChangeReqDTO) {
        return empDataHandlerService.changeMyPassword(passwordChangeReqDTO);
    }

    //news section

    public ResponseEntity<List<NewsRespDTO>> getNews(String empId) {
        return newsServiceHandler.getNews(empId);
    }

    public void markNewsAsRead(NewsReqDTO newsReqDTO) {
        newsService.markAsRead(newsReqDTO.getEmpId(),newsReqDTO.getNewsId());
    }

    public void markAllNewsAsRead(String empId) {
        newsService.markAllAsRead(empId);
    }

    //Leave Section
    public ResponseEntity<String> applyOrUpdateLeave(LeaveRequestDto leaveRequest) {
        return leaveRequestHandler.applyOrUpdateLeave(leaveRequest);
    }

    public ResponseEntity<String> deleteLeaveRequest(int id) {
        return leaveRequestHandler.deleteLeaveRequest(id);
    }

    public ResponseEntity<?> getMyLeaveHistory(String empId) {
        return leaveRequestHandler.getMyLeaveHistory(empId);
    }

    // Attendance section

    public ResponseEntity<String> takingAttendance(AttendanceRequestDto attendanceRequestDto) {
        return attendanceRequestHandler.takingAttendance(attendanceRequestDto);
    }

    public ResponseEntity<?> getMyAttendanceDetails(String empId) {
       return attendanceRequestHandler.getMyAttendanceDetails(empId);
    }

    //Salary section
    public ResponseEntity<?> getMySalaryDetails(String empId) {
        return salaryServiceHandler.getEmployeeSalaryDetails(empId);
    }

}
