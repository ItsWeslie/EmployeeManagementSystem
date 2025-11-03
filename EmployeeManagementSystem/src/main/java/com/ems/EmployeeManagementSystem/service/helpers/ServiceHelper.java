package com.ems.EmployeeManagementSystem.service.helpers;

import com.ems.EmployeeManagementSystem.exceptionHandling.LeaveNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.ResourceNotFound;
import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.model.enums.TimeSlot;
import com.ems.EmployeeManagementSystem.repository.AttendanceRepo;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveRequestRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final EmployeeRepo employeeRepo;
    private final LeaveRequestRepo leaveRequestRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;
    private final AttendanceRepo attendanceRepo;

    public Employee isValidEmployee(String empId) {
        return employeeRepo.findByEmpId(empId).orElseThrow(() -> new ResourceNotFound("Employee Not Found"));
    }

    public boolean isEmployeeExist(String empId)
    {
        return employeeRepo.findByEmpId(empId).isPresent();
    }

    public boolean isOverlappingLeaveExists(String empId, LocalDate start, LocalDate end, Integer excludeId) {


        List<LeaveRequest> leaves = leaveRequestRepo.findByEmployeeEmpId(empId)
                .orElseThrow(()->new LeaveNotFoundException("Leave request not found"));

        for (LeaveRequest lr : leaves) {
            if (excludeId != null && lr.getId() == excludeId) continue;
            if (!(lr.getEndDate().isBefore(start) || lr.getStartDate().isAfter(end))) {
                return true;
            }
        }
        return false;
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

    public boolean checkIfEmployeeExists(String empId) {
        String empID = employeeRepo.findEmployeeByEmpId(empId);
        return !empID.isEmpty();
    }

    public Attendance findExistingAttendance(String empId, LocalDate date) {
        return attendanceRepo.findByEmpIdAndDate(empId,date);
    }

    public TimeSlot isTimeIsWithinRange(LocalTime time) {

        LocalTime morningRange1 = LocalTime.of(8,0);
        LocalTime morningRange2 = LocalTime.of(9,30);

        LocalTime noonRange1 = LocalTime.of(12,30);
        LocalTime noonRange2 = LocalTime.of(13,30);

        if (!time.isBefore(morningRange1) && !time.isAfter(morningRange2)) {
            return TimeSlot.MORNING;
        }
        if (!time.isBefore(noonRange1) && !time.isAfter(noonRange2)) {
            return TimeSlot.NOON;
        }
        return TimeSlot.INVALID;
    }

    public boolean isRequestedDateValid(LocalDate date,int month, Year year) {
        LocalDate today = LocalDate.now();
        Year currentYear = Year.now();

        return today.equals(date) && today.getMonthValue()==month&& currentYear.equals(year);
    }

}
