package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByStatus(LeaveStatus leaveStatus);

    List<LeaveRequest> findByEmployeeEmpId(String empId);

    @Query(value = "select max(leave_type) as mostUsedLeaveType from leave_request_table where emp_id=?", nativeQuery = true)
    String getMostUsedLeaveType(String empId);
}
