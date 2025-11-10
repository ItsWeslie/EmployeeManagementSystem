package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.PasswordChangeReqDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface EMPDataIF {
    public ResponseEntity<?> getMyData(@PathVariable("email") String email);
    public ResponseEntity<?> updateMyData(@PathVariable("empId") String empId,
                                          @RequestPart(value = "imageData",required = false) MultipartFile image);

    public ResponseEntity<?> changeMyPassword(@RequestBody PasswordChangeReqDTO passwordChangeReqDTO);

}
