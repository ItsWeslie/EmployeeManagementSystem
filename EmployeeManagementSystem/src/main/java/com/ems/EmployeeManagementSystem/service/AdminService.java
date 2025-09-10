package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.service.servicehandlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final EmployeeServiceHandler employeeServiceHandler;
    private final AttendanceServiceHandler attendanceServiceHandler;
    private final LeaveServiceHandler leaveServiceHandler;
    private final SalaryServiceHandler salaryServiceHandler;
    private final NewsServiceHandler  newsServiceHandler;

    //Employee service section

    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return employeeServiceHandler.getEmployees();
    }

    public ResponseEntity<Employee> addEmployee(EmployeeRequestDTO employeeRequestDTO){
        return employeeServiceHandler.addEmployee(employeeRequestDTO);
    }

    public ResponseEntity<?> updateEmployee(long id, EmployeeRequestDTO employee) {
        return employeeServiceHandler.updateEmployee(id, employee);
    }

    public ResponseEntity<String> deleteEmployee(long id) {
        return employeeServiceHandler.deleteEmployee(id);
    }

    // Attendance service section

    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords() {
        return attendanceServiceHandler.getAttendanceRecords();
    }

    public ResponseEntity<?> updateAttendanceStatus(int attendanceId, AttendanceRequestDto attendanceRequestDto) {
        return attendanceServiceHandler.updateAttendanceStatus(attendanceId, attendanceRequestDto);
    }

    public ResponseEntity<String> deleteAttendance(int attendanceId) {
        return attendanceServiceHandler.deleteAttendance(attendanceId);
    }

    //Leave Services

    public ResponseEntity<List<LeaveResponseDTO>> getLeaveRecords() {
        return leaveServiceHandler.getLeaveRecords();
    }

    public ResponseEntity<String> approveLeave(int leaveRequestId) {
        return leaveServiceHandler.approveLeave(leaveRequestId);
    }

    public ResponseEntity<String> rejectLeave(int id) {
       return leaveServiceHandler.rejectLeave(id);
    }

    public ResponseEntity<String> addSalary(SalaryRequestDTO salaryRequestDTO) {
        return salaryServiceHandler.addSalary(salaryRequestDTO);
    }


    //News service section
    public ResponseEntity<List<News>> getNews() {
        return newsServiceHandler.getNews();
    }

    public ResponseEntity<?> addNews(News news) {
       return newsServiceHandler.addNews(news);
    }

    public ResponseEntity<String> updateNews(int newsId, News news) {
        return newsServiceHandler.updateNews(newsId, news);
    }

    public ResponseEntity<String> deleteNews(int newsId) {
        return newsServiceHandler.deleteNews(newsId);
    }

}
