package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.dto.LeaveResponseDTO;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.model.LeaveStatus;
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

@Service
@RequiredArgsConstructor
public class LeaveServiceHandler {

    private final LeaveRequestRepo leaveRequestRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;

    public ResponseEntity<List<LeaveResponseDTO>> getLeaveRecords() {

        List<LeaveRequest> leaveRequests =  leaveRequestRepo.findAll();
        List<LeaveResponseDTO> leaveResponses = new ArrayList<>();
        for (LeaveRequest leaveRequest1 : leaveRequests) {
            LeaveResponseDTO leaveResponseDTO = new LeaveResponseDTO();

            leaveResponseDTO.setLeaveReason(leaveRequest1.getReason());
            leaveResponseDTO.setLeaveId(leaveRequest1.getId());
            leaveResponseDTO.setStartDate(leaveRequest1.getStartDate());
            leaveResponseDTO.setEndDate(leaveRequest1.getEndDate());
            leaveResponseDTO.setEmpId(leaveRequest1.getEmployee().getEmpId());
            leaveResponseDTO.setName(leaveRequest1.getEmployee().getName());
            leaveResponseDTO.setDepartment(leaveRequest1.getEmployee().getDepartment());
            leaveResponseDTO.setLeaveStatus(leaveRequest1.getStatus());
            leaveResponses.add(leaveResponseDTO);
        }
        return ResponseEntity.ok(leaveResponses);
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
