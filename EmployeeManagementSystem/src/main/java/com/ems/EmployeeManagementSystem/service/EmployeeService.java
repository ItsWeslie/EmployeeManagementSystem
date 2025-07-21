package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import com.ems.EmployeeManagementSystem.exceptionHandling.GlobalExceptionHandler;
import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveNotFoundException;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveRequestRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final NewsRepo newsRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;
    //private LeaveSummary leaveSummary;
    private final LeaveRequestRepo leaveRequestRepo;
    private final GlobalExceptionHandler globalExceptionHandler;



    //Helper Section
    public Employee toEntity(EmployeeRequestDTO dto) {
        Employee emp = new Employee();

        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setAddress(dto.getAddress());
        emp.setDepartment(dto.getDepartment());
        emp.setBloodGroup(dto.getBloodGroup());
        emp.setCity(dto.getCity());
        emp.setState(dto.getState());
        emp.setDob(dto.getDob());
        emp.setGender(dto.getGender());
        emp.setFatherName(dto.getFatherName());
        emp.setNationality(dto.getNationality());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setSpouseName(dto.getSpouseName());
        emp.setImageName(dto.getImageName());
        emp.setImageType(dto.getImageType());
        emp.setImageData(dto.getImageData());
        return emp;
    }


    public EmployeeResponseDTO toResponseDto(Employee emp) {

            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setEmpId(emp.getEmpId());
            dto.setName(emp.getName());
            dto.setEmail(emp.getEmail());
            dto.setPhone(emp.getPhone());
            dto.setUserName(emp.getUserName());
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

        return dto;
    }

    public int findRemainingLeaves(String emp_id)
    {
        int days=0;
        Optional<?> optional = leaveSummaryRepo.findRemainingLeaveByEmpId(emp_id);
        if(optional.isPresent())
        {
            days = (int) optional.get();
        }

        System.out.println(days);

        return days;
    }

    public boolean isOverlappingLeaveExists(String empId, LocalDate start, LocalDate end, Integer excludeId) {


        List<LeaveRequest> leaves = leaveRequestRepo.findByEmployeeEmpId(empId);

        for (LeaveRequest lr : leaves) {
            if (excludeId != null && lr.getId() == excludeId) continue;
            if (!(lr.getEndDate().isBefore(start) || lr.getStartDate().isAfter(end))) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> getMyData(String email) {

        Employee employees = employeeRepo.findByEmail(email).orElse(null);

        if(employees!=null)
        return new ResponseEntity<>(toResponseDto(employees), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?> updateMyData(String empId, EmployeeRequestDTO employeeRequestDTO) {
        
        Employee emp = employeeRepo.findByEmpId(empId).orElse(null);
        
        if(emp!=null)
        {
            emp.setPhone(employeeRequestDTO.getPhone());
            emp.setEmail(employeeRequestDTO.getEmail());
            emp.setAddress(employeeRequestDTO.getAddress());
            emp.setDepartment(employeeRequestDTO.getDepartment());
            emp.setBloodGroup(employeeRequestDTO.getBloodGroup());
            emp.setCity(employeeRequestDTO.getCity());
            emp.setState(employeeRequestDTO.getState());
            emp.setDob(employeeRequestDTO.getDob());
            emp.setGender(employeeRequestDTO.getGender());
            emp.setFatherName(employeeRequestDTO.getFatherName());
            emp.setSpouseName(employeeRequestDTO.getSpouseName());
            emp.setImageName(employeeRequestDTO.getImageName());
            emp.setImageType(employeeRequestDTO.getImageType());
            emp.setImageData(employeeRequestDTO.getImageData());
            emp.setMaritalStatus(employeeRequestDTO.getMaritalStatus());
            
            employeeRepo.save(emp);
            return new ResponseEntity<>("User data updated successfully", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getNews() {

        return new ResponseEntity<>(newsRepo.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<String> applyOrUpdateLeave(LeaveRequestDto leaveRequest) {

        String empId = leaveRequest.getEmpId();
        Integer leaveRequestId = leaveRequest.getLeaveId(); // null for new

        // Fetch employee
        Employee employee = employeeRepo.findByEmpId(empId).orElse(null);

        // Dates and duration
        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();


        YearMonth currentMonth = YearMonth.now();
        YearMonth leaveMonth = YearMonth.from(startDate);

        if (startDate.isBefore(LocalDate.now()) || !leaveMonth.equals(currentMonth)) {
            return ResponseEntity.badRequest().body("Leaves can only be applied for the current month and not in the past");
        }

        if (endDate.isBefore(startDate)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("End date cannot be before start date");
        }

        int newNoOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if(newNoOfDays>7)
        {
            return ResponseEntity.badRequest().body("Cannot apply leave more than 7 days. Kindly contact HR for more details!");
        }

        // Check for overlapping leave (exclude current leaveRequestId if updating)
        boolean hasOverlap = isOverlappingLeaveExists(empId, startDate, endDate, leaveRequestId);
        if (hasOverlap) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Leave dates overlap with existing leave request");
        }

        // Validate leave balance
        int remainingLeave = findRemainingLeaves(empId);

        // Adjust for updates: If updating, consider old duration
        try {
            if (leaveRequestId != null) {
                LeaveRequest existing = leaveRequestRepo.findById(leaveRequestId)
                        .orElseThrow(() -> new LeaveNotFoundException("Leave request not found"));

                int oldDays = (int) ChronoUnit.DAYS.between(existing.getStartDate(), existing.getEndDate()) + 1;
                remainingLeave += oldDays; // allow reusing old days
            }
        }catch (LeaveNotFoundException e) {
                   globalExceptionHandler.handleLeaveNotFoundException(e);
        }


        if (newNoOfDays > remainingLeave) {
            return ResponseEntity.badRequest()
                    .body("Leave duration (" + newNoOfDays + " days) exceeds remaining leaves (" + remainingLeave + " days)");
        }

        // Create or update
        LeaveRequest leaveRequestToSave = (leaveRequestId != null)
                ? leaveRequestRepo.findById(leaveRequestId).orElse(new LeaveRequest())
                : new LeaveRequest();

        leaveRequestToSave.setEmployee(employee);
        leaveRequestToSave.setStartDate(startDate);
        leaveRequestToSave.setEndDate(endDate);
        leaveRequestToSave.setReason(leaveRequest.getLeaveReason());
        leaveRequestToSave.setLeaveType(leaveRequest.getLeaveType());

        leaveRequestRepo.save(leaveRequestToSave);

        String message = (leaveRequestId != null) ? "Leave request updated successfully" : "Leave applied successfully";
        return ResponseEntity.ok(message);
    }

    public ResponseEntity<String> deleteLeaveRequest(int id) {

        System.out.println("delete function called");
        System.out.println(id);
        Optional<?> optional = leaveRequestRepo.findById(id);

        if(optional.isPresent()) {
            leaveRequestRepo.deleteById(id);
            return ResponseEntity.ok("Leave request deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
    }

    public ResponseEntity<?> getMyLeaveHistory(String empId) {

        Employee employee = employeeRepo.findByEmpId(empId).orElse(null);

        if(employee!=null) {
            return ResponseEntity.ok(leaveRequestRepo.findByEmployeeEmpId(empId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    }

    public ResponseEntity<?> getMostUsedLeave(String empId) {
        return ResponseEntity.ok().body(leaveRequestRepo.getMostUsedLeaveType(empId));
    }
}
