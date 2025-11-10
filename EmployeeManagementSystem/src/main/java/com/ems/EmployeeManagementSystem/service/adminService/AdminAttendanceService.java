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

@Service
@RequiredArgsConstructor
public class AdminAttendanceService implements AdminAttendanceServiceIF {

    private final AttendanceRepo attendanceRepo;
    private final ServiceHelper serviceHelper;

    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceRecords() {

        List<Attendance> attendance = attendanceRepo.findAll();

        List<AttendanceResponseDTO> attendanceResponse = new ArrayList<>();

        for (Attendance attendance1 : attendance) {

            AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO();

            attendanceResponseDTO.setAttendanceId(attendance1.getId());
            attendanceResponseDTO.setAttendanceStatus(attendance1.getStatus());
            attendanceResponseDTO.setDate(attendance1.getDate());
            attendanceResponseDTO.setTime(attendance1.getTime());
            attendanceResponseDTO.setMonth(attendance1.getMonth());
            attendanceResponseDTO.setYear(attendance1.getYear());
            attendanceResponseDTO.setEmpId(attendance1.getEmployee().getEmpId());
            attendanceResponseDTO.setName(attendance1.getEmployee().getName());
            attendanceResponseDTO.setDepartment(attendance1.getEmployee().getDepartment());

            attendanceResponse.add(attendanceResponseDTO);
        }

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
