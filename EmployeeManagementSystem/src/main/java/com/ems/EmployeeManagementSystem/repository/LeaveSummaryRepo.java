package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveSummaryRepo extends JpaRepository<LeaveSummary, Integer> {

    boolean existsByEmployeeIdAndYearAndMonth(Employee employee, int year, int month);

    @Query(value = "select remaining_leave from leave_summary_table where emp_id=?", nativeQuery = true)
    Optional<?> findRemainingLeaveByEmpId(String emp_id);

    @Query(value = "select * from leave_summary_table where emp_id=?", nativeQuery = true)
    LeaveSummary findByEmpId(String empId);
}
