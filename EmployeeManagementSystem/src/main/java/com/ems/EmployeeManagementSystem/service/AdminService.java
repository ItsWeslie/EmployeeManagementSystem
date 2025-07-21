package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.dto.LeaveSummaryDto;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveRequestRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmployeeRepo employeeRepo;
    private final NewsRepo newsRepo;
    private final LeaveRequestRepo leaveRequestRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;
    private final LeaveSummaryDto leaveSummaryDto;
    private final LeaveSummary leaveSummary;


    public Employee toEntity(EmployeeRequestDTO dto) {
        Employee emp = new Employee();
        emp.setEmpId(dto.getEmpId());
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setUserName(dto.getUserName());
        emp.setPassword(passwordEncoder().encode(dto.getPassword()));
        emp.setRole(dto.getRole());
        emp.setAddress(dto.getAddress());
        emp.setDepartment(dto.getDepartment());
        emp.setBloodGroup(dto.getBloodGroup());
        emp.setCity(dto.getCity());
        emp.setState(dto.getState());
        emp.setDob(dto.getDob());
        emp.setGender(dto.getGender());
        emp.setFatherName(dto.getFatherName());
        emp.setJoinDate(dto.getJoinDate());
        emp.setNationality(dto.getNationality());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setSpouseName(dto.getSpouseName());
        emp.setImageName(dto.getImageName());
        emp.setImageType(dto.getImageType());
        emp.setImageData(dto.getImageData());
        emp.setWorkLocation(dto.getWorkLocation());
        return emp;
    }


    public List<EmployeeResponseDTO> toResponseDto(List<Employee> employees) {
        List<EmployeeResponseDTO> dtoList = new ArrayList<>();

        for (Employee emp : employees) {
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setId(emp.getId());
            dto.setEmpId(emp.getEmpId());
            dto.setName(emp.getName());
            dto.setEmail(emp.getEmail());
            dto.setPhone(emp.getPhone());
            dto.setUserName(emp.getUserName());
            dto.setRole(emp.getRole());
            dto.setAddress(emp.getAddress());
            dto.setDepartment(emp.getDepartment());
            dto.setBloodGroup(emp.getBloodGroup());
            dto.setCity(emp.getCity());
            dto.setState(emp.getState());
            dto.setDob(emp.getDob());
            dto.setGender(emp.getGender());
            dto.setFatherName(emp.getFatherName());
            dto.setJoinDate(emp.getJoinDate());
            dto.setNationality(emp.getNationality());
            dto.setMaritalStatus(emp.getMaritalStatus());
            dto.setSpouseName(emp.getSpouseName());
            dto.setImageName(emp.getImageName());
            dto.setImageType(emp.getImageType());
            dto.setImageData(emp.getImageData());
            dto.setWorkLocation(emp.getWorkLocation());

            dtoList.add(dto);
        }

        return dtoList;
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //Employee service section

    public List<EmployeeResponseDTO> getEmployees() {
        List<Employee> emp = employeeRepo.findAll();
        return toResponseDto(emp);
    }

    public ResponseEntity<String> addEmployee(EmployeeRequestDTO employeeRequestDTO, MultipartFile image) throws IOException {

        employeeRequestDTO.setImageName(image.getOriginalFilename());
        employeeRequestDTO.setImageType(image.getContentType());
        employeeRequestDTO.setImageData(image.getBytes());
        Employee emp = toEntity(employeeRequestDTO);

        employeeRepo.save(emp);

        leaveSummary.setMonth(YearMonth.now().getMonth().getValue());
        leaveSummary.setYear(YearMonth.now().getYear());
        leaveSummary.setEmployee(emp);

        leaveSummaryRepo.save(leaveSummary);


        return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<String> updateEmployee(String empId, EmployeeRequestDTO employee) {
        Employee emp=employeeRepo.findByEmpId(empId).orElse(null);

        if(emp!=null) {
            emp.setName(employee.getName());
            emp.setEmail(employee.getEmail());
            emp.setPhone(employee.getPhone());
            emp.setUserName(employee.getUserName());
            emp.setRole(employee.getRole());
            emp.setAddress(employee.getAddress());
            emp.setDepartment(employee.getDepartment());
            emp.setBloodGroup(employee.getBloodGroup());
            emp.setCity(employee.getCity());
            emp.setState(employee.getState());
            emp.setDob(employee.getDob());
            emp.setGender(employee.getGender());
            emp.setFatherName(employee.getFatherName());
            emp.setJoinDate(employee.getJoinDate());
            emp.setNationality(employee.getNationality());
            emp.setMaritalStatus(employee.getMaritalStatus());
            emp.setSpouseName(employee.getSpouseName());
            emp.setImageName(employee.getImageName());
            emp.setImageType(employee.getImageType());
            emp.setImageData(employee.getImageData());
            emp.setWorkLocation(employee.getWorkLocation());

            employeeRepo.save(emp);
            return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteEmployee(long id) {

        Employee emp = employeeRepo.findById(id).orElse(null);
        if (emp!=null) {
            employeeRepo.deleteById(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }


    //News service section

    public ResponseEntity<List<News>> getNews() {
        List<News> news =newsRepo.findAll();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    public ResponseEntity<String> addNews(News news) {
        newsRepo.save(news);
        return new ResponseEntity<>("News added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateNews(int newsId, News news) {
        News news1 = newsRepo.findById(newsId).orElse(null);
        if(news1!=null) {
            news1.setNewsTitle(news.getNewsTitle());
            news1.setNewsContent(news.getNewsContent());
            news1.setNewsDate(news.getNewsDate());
            news1.setNewsTag(news.getNewsTag());
            newsRepo.save(news1);
            return new ResponseEntity<>("News updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteNews(int newsId) {
        News news = newsRepo.findById(newsId).orElse(null);
        if (news!=null) {
            newsRepo.delete(news);
            return new ResponseEntity<>("News deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
    }

    //Leave Services

    public ResponseEntity<List<LeaveRequest>> getPendingLeaves() {
        return ResponseEntity.ok(leaveRequestRepo.findByStatus(LeaveStatus.PENDING));
    }

    public ResponseEntity<List<LeaveRequest>> getApprovedLeaves() {
        return ResponseEntity.ok(leaveRequestRepo.findByStatus(LeaveStatus.APPROVED));
    }

    public ResponseEntity<List<LeaveRequest>> getRejectedLeaves() {
        return ResponseEntity.ok(leaveRequestRepo.findByStatus(LeaveStatus.REJECTED));
    }


    public ResponseEntity<String> approveLeave(int leaveRequestId) {

        LeaveRequest leaveRequest = leaveRequestRepo.findById(leaveRequestId).orElse(null);

        if(leaveRequest==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
        }

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Leave request is already " + leaveRequest.getStatus());
        }

        String empId = leaveRequest.getEmployee().getEmpId();

        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();

        int noOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate)+1;

        LeaveSummary leaveSummary1 =leaveSummaryRepo.findByEmpId(empId);

        if(noOfDays>leaveSummary1.getRemainingLeave())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No leave left for the employee "+empId);
        }

        leaveSummary1.setTotalLeave(leaveSummary1.getTotalLeave()+noOfDays);
        leaveSummary1.setLeaveTaken(leaveSummary1.getLeaveTaken()+noOfDays);
        leaveSummary1.setRemainingLeave(leaveSummary1.getRemainingLeave()-noOfDays);

        int existingLLD = leaveSummary1.getLongestLeaveDuration();

            if(noOfDays>existingLLD)
            {
                leaveSummary1.setLongestLeaveDuration(noOfDays);
            }

            leaveSummaryRepo.save(leaveSummary1);

            leaveRequest.setStatus(LeaveStatus.APPROVED);
            leaveRequestRepo.save(leaveRequest);

        return ResponseEntity.ok("Leave approved successfully");
    }

    public ResponseEntity<String> rejectLeave(int id) {
        LeaveRequest leaveRequest = leaveRequestRepo.findById(id).orElse(null);

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Leave request is already " + leaveRequest.getStatus());
        }

        if(leaveRequest!=null) {
            leaveRequest.setStatus(LeaveStatus.REJECTED);
            leaveRequestRepo.save(leaveRequest);
            return ResponseEntity.ok("Leave rejected successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
    }
}
