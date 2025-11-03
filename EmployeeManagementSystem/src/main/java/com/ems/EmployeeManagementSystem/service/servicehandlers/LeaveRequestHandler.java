package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.dto.LeaveDataDTO;
import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import com.ems.EmployeeManagementSystem.exceptionHandling.EmployeeNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.GlobalExceptionHandler;
import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveSummaryNotFoundException;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import com.ems.EmployeeManagementSystem.model.enums.LeaveType;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveRequestRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import com.ems.EmployeeManagementSystem.service.helpers.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestHandler {

    private final EmployeeRepo employeeRepo;
    private final ServiceHelper serviceHelper;
    private final LeaveRequestRepo leaveRequestRepo;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final LeaveSummaryRepo leaveSummaryRepo;

    public ResponseEntity<String> applyOrUpdateLeave(LeaveRequestDto leaveRequest) {

        String empId = leaveRequest.getEmpId();
        Integer leaveRequestId = leaveRequest.getLeaveId(); // null for new

        // Fetch employee
        Employee employee = employeeRepo.findByEmpId(empId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee Not Found for empId : " + empId));

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
        boolean hasOverlap = serviceHelper.isOverlappingLeaveExists(empId, startDate, endDate, leaveRequestId);
        if (hasOverlap) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Leave dates overlap with existing leave request");
        }

        // Validate leave balance
        int remainingLeave = serviceHelper.findRemainingLeaves(empId);

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
        Optional<?> optional = leaveRequestRepo.findById(id);

        if(optional.isPresent()) {
            leaveRequestRepo.deleteById(id);
            return ResponseEntity.ok("Leave request deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
    }

    public ResponseEntity<?> getMyLeaveHistory(String empId) {

        List<LeaveRequest> leaveRequests = leaveRequestRepo.findLeaveRequestsByEmployee_EmpId(empId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found"));

        List<LeaveType> leaveTypes = leaveRequestRepo.getLeaveTypeByEmployee_EmpId(empId)
                .orElseThrow(()-> new LeaveNotFoundException("Leave request not found for empId "+empId));

        LeaveSummary leaveSummary = leaveSummaryRepo.findByEmpId(empId)
                .orElseThrow(()-> new LeaveSummaryNotFoundException("Leave summary not found for empId: " + empId));

        Map<LeaveType,Integer> leaveMap = new HashMap<>();
        
        leaveMap.put(LeaveType.Casual,0);
        leaveMap.put(LeaveType.Sick,0);
        leaveMap.put(LeaveType.Paid,0);
        leaveMap.put(LeaveType.WFH,0);

        leaveTypes.forEach(leaveType -> {
            leaveMap.put(leaveType,leaveMap.getOrDefault(leaveType,0)+1);
        });

        String mostTakenLeave = findMostTakenLeave(leaveMap);

        LeaveDataDTO leaveDataDTO = new LeaveDataDTO();
        leaveDataDTO.setEmpId(empId);
        leaveDataDTO.setLeaveTaken(leaveSummary.getLeaveTaken());
        leaveDataDTO.setLongestLeave(leaveSummary.getLongestLeaveDuration());
        leaveDataDTO.setTotalLeave(leaveSummary.getTotalLeave());
        leaveDataDTO.setRemainingLeave(leaveSummary.getRemainingLeave());
        leaveDataDTO.setMostTakenLeave(mostTakenLeave);
        leaveDataDTO.setLeavePieChart(leaveMap);
        leaveDataDTO.setLeaveRequests(leaveRequests);

        return ResponseEntity.ok(leaveDataDTO);
    }

    public String findMostTakenLeave(Map<LeaveType,Integer> leaveMap) {

        int count = 0;
        String mostTakenLeave="";

        for(LeaveType leaveType : leaveMap.keySet()) {
            if(leaveMap.get(leaveType) > count) {
                count = leaveMap.get(leaveType);
                mostTakenLeave = leaveType.toString();
            }
        }
        return mostTakenLeave;
    }
}
