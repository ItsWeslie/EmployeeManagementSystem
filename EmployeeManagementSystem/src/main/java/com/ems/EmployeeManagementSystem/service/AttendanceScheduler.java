package com.ems.EmployeeManagementSystem.service;


import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import com.ems.EmployeeManagementSystem.repository.AttendanceRepo;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.HolidayRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final EmployeeRepo employeeRepo;
    private final AttendanceRepo attendanceRepo;
    private final HolidayRepo holidayRepo;
    private final LeaveSummaryRepo leaveSummaryRepo;

    //mark attendance as absent
    public void markAttendance(Employee employee,LocalDate date) {
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(date);
        attendance.setTime(LocalTime.now());
        attendance.setStatus(AttendanceStatus.ABSENT);
        attendance.setMonth(YearMonth.now().getMonthValue());
        attendance.setYear(Year.now());

        attendanceRepo.save(attendance);
    }

    // mark weekends and holidays as holidays
    public void markWeekendsOrHolidays(Employee employee,LocalDate date) {
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(date);
        attendance.setTime(LocalTime.now());
        attendance.setStatus(AttendanceStatus.HOLIDAY);
        attendance.setMonth(YearMonth.now().getMonthValue());
        attendance.setYear(Year.now());

        attendanceRepo.save(attendance);
    }

    // deduct leaves when attendance is absent
    public void deductLeave(Employee employee)
    {
        LeaveSummary leaveSummary = leaveSummaryRepo.findByEmpIdAndMonthAndYear(
                employee.getEmpId(),LocalDate.now().getMonthValue(),Year.now()
        );
        if(leaveSummary!=null&&leaveSummary.getRemainingLeave()>0) {

                leaveSummary.setLeaveTaken(leaveSummary.getLeaveTaken()+1);
                leaveSummary.setRemainingLeave(leaveSummary.getRemainingLeave()-1);
                leaveSummary.setTotalLeave(leaveSummary.getTotalLeave()+1);
                leaveSummaryRepo.save(leaveSummary);
        }

        //Loss of pay implementation
    }

    //mark attendance as absent at 1:31pm every day from monday to friday when employee not present within given timeline
    @Scheduled(cron = "0 31 13 * * MON-FRI")
    public void attendanceScheduling() {

        LocalDate today = LocalDate.now();

        List<Employee> employees = employeeRepo.findAll();

        boolean holidayExists = holidayRepo.existsHolidayByDate(today);
        if (holidayExists) {
            for(Employee employee : employees) {
                boolean hasAttendance = attendanceRepo.existsByEmployeeAndDateAndStatusIn(
                        employee, today, List.of(AttendanceStatus.HOLIDAY)
                );
                if (!hasAttendance) {
                    markWeekendsOrHolidays(employee, today);
                }
            }
            System.out.println("Holiday exists so no absent today");
            return;
        }

        for(Employee employee : employees) {
            boolean hasAttendance = attendanceRepo.existsByEmployeeAndDateAndStatusIn(
                    employee,today, List.of(AttendanceStatus.PRESENT, AttendanceStatus.HALF_DAY));

            if (!hasAttendance) {

                markAttendance(employee,today);
                deductLeave(employee);

                //After salary implementatoion have to write loss of pay logic
            }
        }

        System.out.println("Auto-marked absent for non-attending employees on " + today);
    }

    //mark weekends as holidays every saturday and sunday
    @Scheduled(cron = "0 0 12 * * SAT-SUN")
    public void weekEndAttendance() {

        LocalDate today = LocalDate.now();

        List<Employee> employees = employeeRepo.findAll();
        for(Employee employee : employees) {
            boolean hasAttendance = attendanceRepo.existsByEmployeeAndDateAndStatusIn(
                    employee,today, List.of(AttendanceStatus.HOLIDAY)
            );
            if (!hasAttendance) {
                markWeekendsOrHolidays(employee,today);
            }
        }

    }
}
