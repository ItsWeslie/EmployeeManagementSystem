package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.dto.AttendanceDashDTO;
import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import com.ems.EmployeeManagementSystem.dto.Last7DaysAttendanceDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.AttendanceNotFoundException;
import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.ems.EmployeeManagementSystem.model.enums.TimeSlot;
import com.ems.EmployeeManagementSystem.repository.AttendanceRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import com.ems.EmployeeManagementSystem.service.helpers.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceRequestHandler {

    private final AttendanceRepo attendanceRepo;
    private final ServiceHelper serviceHelper;
    private final LeaveSummaryRepo leaveSummaryRepo;

    private final int TOTAL_WORKING_DAY = 249;


    public ResponseEntity<String> markAttendancePresent(Employee employee,AttendanceRequestDto attendanceRequestDto) {

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(attendanceRequestDto.getDate());
        attendance.setTime(attendanceRequestDto.getTime());
        attendance.setMonth(attendanceRequestDto.getMonth());
        attendance.setYear(attendanceRequestDto.getYear());
        attendance.setStatus(AttendanceStatus.PRESENT);

        attendanceRepo.save(attendance);
        return ResponseEntity.ok("Attendance Marked successfully for full day");
    }

    public ResponseEntity<String> markAttendanceHalfDay(Employee employee,AttendanceRequestDto attendanceRequestDto) {
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(attendanceRequestDto.getDate());
        attendance.setTime(attendanceRequestDto.getTime());
        attendance.setMonth(attendanceRequestDto.getMonth());
        attendance.setYear(attendanceRequestDto.getYear());
        attendance.setStatus(AttendanceStatus.HALF_DAY);

        attendanceRepo.save(attendance);
        return ResponseEntity.ok("Attendance Marked successfully for half day");
    }

    public ResponseEntity<String> takingAttendance(AttendanceRequestDto attendanceRequestDto) {

        String empId = attendanceRequestDto.getEmpId();
        Employee employee = serviceHelper.isValidEmployee(empId);// have to modify this employee fetch logic

        Attendance existingAttendance = serviceHelper.findExistingAttendance(empId,attendanceRequestDto.getDate());

        if(existingAttendance!=null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Attendance already taken");
        }

        LocalTime time = attendanceRequestDto.getTime();
        TimeSlot timeSlot = serviceHelper.isTimeIsWithinRange(time);

        if(timeSlot.equals(TimeSlot.INVALID))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("TimeSlot is invalid");
        }

        boolean isDateValid =
                serviceHelper.isRequestedDateValid(attendanceRequestDto.getDate(),
                        attendanceRequestDto.getMonth(), attendanceRequestDto.getYear());

        if(!isDateValid) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Attendance date is invalid");
        }

        return switch (timeSlot) {
            case MORNING -> markAttendancePresent(employee, attendanceRequestDto);
            case NOON -> markAttendanceHalfDay(employee, attendanceRequestDto);
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are absent today");
        };

    }

    public ResponseEntity<?> getMyAttendanceDetails(String empId) {

        List<Attendance> attendanceList = attendanceRepo
                .getAttendanceByEmployee_empIdAndYear(empId,Year.now())
                .orElseThrow(()->new AttendanceNotFoundException("Attendance not found for empId: "+empId));

        List<Last7DaysAttendanceDTO> last7DaysAttendanceList = findLast7DaysAttendance(empId);

        AttendanceStatus attendanceStatus = (AttendanceStatus) findAttendanceStatus(attendanceList)
                .orElse(null);

        int totalPresentInCurrentMonth = findTotalNoOfPresentForMonth(attendanceList);

        int totalDaysInCurrentMonth = findTotalDaysInCurrentMonth();

        int totalLeaveTaken = findTotalLeaveTaken(empId);

        double totalAttendancePercentage = findTotalAttendancePercentage(attendanceList);

        double totalAttendancePercentageForCurrentMonth =
                findTotalAttendancePercentageForCurrentMonth(totalPresentInCurrentMonth);

        AttendanceDashDTO attendanceDashDTO = new AttendanceDashDTO();

        attendanceDashDTO.setAttendanceStatus(attendanceStatus);
        attendanceDashDTO.setOverAllAttendance(attendanceList);
        attendanceDashDTO.setLast7DaysAttendance(last7DaysAttendanceList);
        attendanceDashDTO.setTotalPresentInCurrentMonth(totalPresentInCurrentMonth);
        attendanceDashDTO.setTotalDaysInCurrentMonth(totalDaysInCurrentMonth);
        attendanceDashDTO.setTotalWorkingDays(TOTAL_WORKING_DAY);
        attendanceDashDTO.setLeaveTaken(totalLeaveTaken);
        attendanceDashDTO.setMonthlyAttendancePercentage(totalAttendancePercentageForCurrentMonth);
        attendanceDashDTO.setTotalAttendancePercentage(totalAttendancePercentage);

        return ResponseEntity.ok(attendanceDashDTO);
    }

    private int findTotalNoOfPresentForMonth(List<Attendance> attendanceList) {

        int currentMonth = LocalDate.now().getMonthValue();

        return (int) attendanceList.stream()
                .filter(a -> a.getMonth() == currentMonth && a.getStatus() == AttendanceStatus.PRESENT)
                .count();
    }

    private int findTotalDaysInCurrentMonth() {
        YearMonth currentYearMonth = YearMonth.now();
        return currentYearMonth.lengthOfMonth();
    }

    public List<Last7DaysAttendanceDTO> findLast7DaysAttendance(String empId) {

        List<Attendance> attendanceList = attendanceRepo
                .findLast7DaysAttendanceStatusByEmployee_EmpId(empId);

        List<Last7DaysAttendanceDTO> last7DaysAttendanceList = new ArrayList<>();

        attendanceList.forEach(attendance -> {
           Last7DaysAttendanceDTO last7DaysAttendanceDTO = new Last7DaysAttendanceDTO();
           last7DaysAttendanceDTO.setAttendanceStatus(attendance.getStatus());
           last7DaysAttendanceDTO.setDate(DateFormatterHelper(attendance.getDate()));
           last7DaysAttendanceList.add(last7DaysAttendanceDTO);
        });

        return last7DaysAttendanceList;
    }

    private Optional<?> findAttendanceStatus(List<Attendance> attendanceList) {
        LocalDate currentDate = LocalDate.now();

        return attendanceList.stream()
                .filter(attendance -> attendance.getDate().equals(currentDate))
                .map(Attendance::getStatus)
                .findFirst();
    }

    private int findTotalLeaveTaken(String empId) {
        return leaveSummaryRepo.findTotalLeaveByEmployee_empIdAndYear(empId, LocalDate.now().getYear());
    }

    private String DateFormatterHelper(LocalDate date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM,dd", Locale.ENGLISH);
        return date.format(formatter);
    }

    private double findTotalAttendancePercentage(List<Attendance> attendanceList) {

        int totalPresent  = (int) attendanceList.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.PRESENT)
                .count();

        return Math.round(((double) totalPresent / TOTAL_WORKING_DAY) * 100 * 100.0) / 100.0;
    }

    private double findTotalAttendancePercentageForCurrentMonth(int totalPresentInCurrentMonth) {
        return Math.round(((double) totalPresentInCurrentMonth / TOTAL_WORKING_DAY) * 100 * 100.0) / 100.0;
    }

}
