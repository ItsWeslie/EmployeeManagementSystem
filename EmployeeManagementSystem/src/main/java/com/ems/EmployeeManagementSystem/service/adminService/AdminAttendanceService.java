package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.AttendanceRequestDto;
import com.ems.EmployeeManagementSystem.dto.AttendanceResponseDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.ResourceNotFound;
import com.ems.EmployeeManagementSystem.interfaces.AdminAttendanceServiceIF;
import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.repository.AttendanceRepo;
import com.ems.EmployeeManagementSystem.service.helpers.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAttendanceService implements AdminAttendanceServiceIF {

    private final AttendanceRepo attendanceRepo;
    private final ServiceHelper serviceHelper;

    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords() {

        List<AttendanceResponseDTO> attendanceResponse = attendanceRepo.findAll()
                .stream()
                .map(attendance -> {
                    AttendanceResponseDTO dto = new AttendanceResponseDTO();
                    dto.setAttendanceId(attendance.getId());
                    dto.setAttendanceStatus(attendance.getStatus());
                    dto.setDate(attendance.getDate());
                    dto.setTime(attendance.getTime());
                    dto.setMonth(attendance.getMonth());
                    dto.setYear(attendance.getYear());
                    dto.setEmpId(attendance.getEmployee().getEmpId());
                    dto.setName(attendance.getEmployee().getName());
                    dto.setDepartment(attendance.getEmployee().getDepartment());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(attendanceResponse);
    }

    public ResponseEntity<?> updateAttendanceStatus(int attendanceId, AttendanceRequestDto attendanceRequestDto) {
        Attendance attendance = attendanceRepo.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFound("Attendance Not Found"));

        String emp_id = attendance.getEmployee().getEmpId();
        if (!serviceHelper.isEmployeeExist(emp_id)) {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }

        attendance.setStatus(attendanceRequestDto.getAttendanceStatus());
        attendanceRepo.save(attendance);

        return ResponseEntity.ok(attendance.getStatus());
    }

    public ResponseEntity<String> deleteAttendance(int attendanceId) {
        boolean isAttendanceExist = attendanceRepo.existsById(attendanceId);
        if (!isAttendanceExist) {
            return new ResponseEntity<>("Attendance Not Found", HttpStatus.NOT_FOUND);
        }

        attendanceRepo.deleteById(attendanceId);
        return ResponseEntity.ok("Attendance deleted successfully");
    }
}
