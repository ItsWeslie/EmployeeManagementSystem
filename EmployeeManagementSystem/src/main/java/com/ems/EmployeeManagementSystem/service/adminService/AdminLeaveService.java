package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.LeaveResponseDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveSummaryNotFoundException;
import com.ems.EmployeeManagementSystem.interfaces.AdminLeaveServiceIF;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.enums.LeaveStatus;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import com.ems.EmployeeManagementSystem.repository.LeaveRequestRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminLeaveService implements AdminLeaveServiceIF {

    private final LeaveRequestRepo leaveRequestRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;

    public ResponseEntity<List<LeaveResponseDTO>> getLeaveRecords() {

        List<LeaveResponseDTO> leaveResponses =  leaveRequestRepo.findAll()
                .stream()
                .map(leaveRequest -> LeaveResponseDTO.builder()
                        .leaveId(leaveRequest.getId())
                        .empId(leaveRequest.getEmployee().getEmpId())
                        .name(leaveRequest.getEmployee().getName())
                        .department(leaveRequest.getEmployee().getDepartment())
                        .leaveReason(leaveRequest.getReason())
                        .startDate(leaveRequest.getStartDate())
                        .endDate(leaveRequest.getEndDate())
                        .leaveStatus(leaveRequest.getStatus())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(leaveResponses);
    }

    public ResponseEntity<String> approveLeave(int leaveRequestId) {

        LeaveRequest leaveRequest = leaveRequestRepo.findById(leaveRequestId)
                .orElseThrow(()-> new LeaveNotFoundException("Leave request not found for id: " + leaveRequestId));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Leave request is already " + leaveRequest.getStatus());
        }

        String empId = leaveRequest.getEmployee().getEmpId();

        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();

        int noOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate)+1;

        LeaveSummary leaveSummary1 =leaveSummaryRepo.findByEmpId(empId)
                .orElseThrow(()->new LeaveSummaryNotFoundException("Leave summary not found for empId: "+empId));

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
        LeaveRequest leaveRequest = leaveRequestRepo.findById(id)
                .orElseThrow(()-> new LeaveNotFoundException("Leave request not found for id: " + id));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Leave request is already " + leaveRequest.getStatus());
        }

            leaveRequest.setStatus(LeaveStatus.REJECTED);
            leaveRequestRepo.save(leaveRequest);
            return ResponseEntity.ok("Leave rejected successfully");
    }
}
