package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.PasswordChangeReqDTO;
import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @GetMapping("/getMyData/{email}")
    public ResponseEntity<?> getMyData(@PathVariable("email") String email) {
        return employeeProfileService.getMyData(email);
    }

    @PutMapping("/updateMyProfilePic/{empId}")
    public ResponseEntity<?> updateMyData(@PathVariable("empId") String empId,
                                          @RequestPart(value = "imageData",required = false) MultipartFile image
    ) {
        return employeeProfileService.updateMyData(empId,image);
    }

    @PutMapping("/changeMyPassword")
    public ResponseEntity<?> changeMyPassword(@Valid @RequestBody PasswordChangeReqDTO passwordChangeReqDTO) {
        System.out.println("inside changeMyPassword controller");
        return employeeProfileService.changeMyPassword(passwordChangeReqDTO);
    }
}
