package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.dto.PasswordChangeReqDTO;
import com.ems.EmployeeManagementSystem.dto.ProfilePicRespDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.EmployeeNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.GlobalExceptionHandler;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.ProfilePic;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.ProfilePicRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeDataHandler {

    private final EmployeeRepo employeeRepo;
    private final EmployeeResponseDTO employeeResponseDTO;
    private final ProfilePicRepo profilePicRepo;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getMyData(String email) {

        Employee employee = employeeRepo.findByEmail(email).orElse(null);

        if(employee!=null) return employeeResponseDTO.employeeResponse(employee);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateMyData(String empId, MultipartFile image) {

        try {
            Employee employee = employeeRepo.findByEmpId(empId).orElse(null);
            if (employee != null) {
                ProfilePic profilePic = profilePicRepo.findProfilePicByEmployee_EmpId(empId).orElse(new ProfilePic());
                profilePic.setEmployee(employee);
                profilePic.setImageName(image.getOriginalFilename());
                profilePic.setImageType(image.getContentType());
                profilePic.setImageData(image.getBytes());

                profilePicRepo.save(profilePic);

                ProfilePicRespDTO profilePicResp = new ProfilePicRespDTO();
                profilePicResp.setImageName(profilePic.getImageName());
                profilePicResp.setImageType(profilePic.getImageType());
                profilePicResp.setImageData(profilePic.getImageData());

                return ResponseEntity.ok(profilePicResp);
            }
        }
        catch (Exception e) {
            globalExceptionHandler.handleException(e);
        }
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> changeMyPassword(PasswordChangeReqDTO passwordChangeReqDTO) {

        String currentPassword = passwordChangeReqDTO.getCurrentPassword();
        String newPassword = passwordChangeReqDTO.getNewPassword();
        String empId = passwordChangeReqDTO.getEmpId();

        String password = employeeRepo.findEmployeePasswordByEmpId(empId)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee Not Found"));

        if(!passwordEncoder.matches(currentPassword, password))
        {
            return new ResponseEntity<>("Wrong password",HttpStatus.UNAUTHORIZED);
        }

        employeeRepo.updateEmployeePassword(empId,passwordEncoder.encode(newPassword));

        return new ResponseEntity<>("Password changed",HttpStatus.OK);
    }
}
