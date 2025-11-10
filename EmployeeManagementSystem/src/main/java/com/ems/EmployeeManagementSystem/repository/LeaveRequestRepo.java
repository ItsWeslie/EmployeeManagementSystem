package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.LeaveRequest;
import com.ems.EmployeeManagementSystem.enums.LeaveStatus;
import com.ems.EmployeeManagementSystem.enums.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByStatus(LeaveStatus leaveStatus);

    Optional<List<LeaveRequest>> findByEmployeeEmpId(String empId);

//    @Query(value = "select max(leave_type) as mostUsedLeaveType from leave_request_table where emp_id=?", nativeQuery = true)
//    String getMostUsedLeaveType(String empId);

    @Query(value = "select * from leave_request_table where emp_id= :empId order by end_date desc limit 5"
            , nativeQuery = true)
    Optional<List<LeaveRequest>> findLeaveRequestsByEmployee_EmpId(@Param("empId") String empId);

    @Query(value = "select leave_type from leave_request_table where emp_id= :empId", nativeQuery = true )
    Optional<List<LeaveType>> getLeaveTypeByEmployee_EmpId(@Param("empId") String empId);
}
