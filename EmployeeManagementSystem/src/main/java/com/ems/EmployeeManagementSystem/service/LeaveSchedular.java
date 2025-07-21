package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveSchedular {


    private final EmployeeRepo employeeRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;

    @Scheduled(zone = "Asia/Kolkata", cron = "0 0 0 1 1 *")
    public void assignLeaveToEmployee(){
        List<Employee> employees = employeeRepo.findAll();

        YearMonth currentYearMonth = YearMonth.now();

        for (Employee employee : employees) {
            boolean found = leaveSummaryRepo.existsByEmployeeIdAndYearAndMonth(employee,currentYearMonth.getYear(),currentYearMonth.getMonthValue());

            if(!found){
                LeaveSummary leaveSummary = new LeaveSummary();
                leaveSummary.setEmployee(employee);
                leaveSummary.setYear(currentYearMonth.getYear());
                leaveSummary.setMonth(currentYearMonth.getMonthValue());
                leaveSummary.setTotalLeavesAllowed(40);
                leaveSummary.setRemainingLeave(40);
                leaveSummary.setLeaveTaken(0);
                leaveSummary.setTotalLeave(0);
                leaveSummary.setLongestLeaveDuration(0);
                leaveSummaryRepo.save(leaveSummary);
            }
        }
    }
}
