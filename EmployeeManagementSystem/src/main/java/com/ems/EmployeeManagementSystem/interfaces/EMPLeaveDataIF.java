package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.dto.LeaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EMPLeaveDataIF {
    public ResponseEntity<?> getMyLeaveHistory(@PathVariable("emp_id") String emp_id);
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequestDto leaveRequestdto);
    public ResponseEntity<String> updateLeaveRequest(@RequestBody LeaveRequestDto leaveRequestdto);
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("leaveReq_id") int id);
}
