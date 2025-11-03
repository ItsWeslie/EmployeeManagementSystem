package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.dto.*;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.service.servicehandlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

    // salary section
    public ResponseEntity<Salary> addSalary(SalaryRequestDTO salaryRequestDTO) {
        return salaryServiceHandler.addSalary(salaryRequestDTO);
    }

    public ResponseEntity<String> approveSalary(int salaryId) {
        return salaryServiceHandler.approveSalary(salaryId);
    }

    public ResponseEntity<String> updateSalary(SalaryRequestDTO salaryRequestDTO) {
        return salaryServiceHandler.updateSalary(salaryRequestDTO);
    }

    public ResponseEntity<String> deleteSalary(int id) {
        return salaryServiceHandler.deleteSalary(id);
    }

    public ResponseEntity<AdminSalaryResponseDTO> getSalaryDash()
    {
        return salaryServiceHandler.getSalaryDash();
    }

    //News service section
    public ResponseEntity<List<News>> getNews() {
        return newsServiceHandler.getNews();
    }

    public ResponseEntity<?> addNews(News news) {
       return newsServiceHandler.addNews(news);
    }

    public ResponseEntity<String> updateNews(long newsId, News news) {
        return newsServiceHandler.updateNews(newsId, news);
    }

    public ResponseEntity<String> deleteNews(long newsId) {
        return newsServiceHandler.deleteNews(newsId);
    }

}
