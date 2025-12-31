package com.ems.EmployeeManagementSystem.service.employeeService;

import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.dto.PasswordChangeReqDTO;
import com.ems.EmployeeManagementSystem.dto.ProfilePicRespDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.EmployeeNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.GlobalExceptionHandler;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.ProfilePic;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.ProfilePicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmployeeProfileService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeResponseDTO employeeResponseDTO;
    private final ProfilePicRepo profilePicRepo;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getMyData(String email) {

        Employee employee = employeeRepo.findByEmail(email)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee Not Found for email id : " + email));

        return employeeResponseDTO.employeeResponse(employee);
    }

    public ResponseEntity<?> updateMyData(String empId, MultipartFile image) throws IOException {

            Employee employee = employeeRepo.findByEmpId(empId)
                    .orElseThrow(()-> new EmployeeNotFoundException("Employee Not Found for empId : " + empId));

                ProfilePic profilePic = profilePicRepo.findProfilePicByEmployee_EmpId(empId).orElse(new ProfilePic());
                profilePic.setEmployee(employee);
                profilePic.setImageName(image.getOriginalFilename());
                profilePic.setImageType(image.getContentType());
                profilePic.setImageData(image.getBytes());

                profilePicRepo.save(profilePic);

                ProfilePicRespDTO profilePicResp = ProfilePicRespDTO.builder()
                        .imageName(profilePic.getImageName())
                        .imageType(profilePic.getImageType())
                        .imageData(profilePic.getImageData())
                        .build();

                return ResponseEntity.ok(profilePicResp);
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
