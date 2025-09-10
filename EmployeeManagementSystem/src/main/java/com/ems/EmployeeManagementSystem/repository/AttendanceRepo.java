package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.AttendanceStatus;
import com.ems.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    @Query(value = "select * from attendance_table where emp_id=? and date = ?", nativeQuery = true)
    Attendance findByEmpIdAndDate(String empId, LocalDate date);

    @Query(value = "select  * from attendance_table where emp_id =? ", nativeQuery = true)
    List<Attendance> findByEmpId(String empId);

    @Query(value = "select count(status) from attendance_table " +
            "where emp_id=? " +
            "and " +
            "month=? " +
            "and status = 'PRESENT';" ,nativeQuery = true)
    int countPresentByEmpIdAndMonth(String empId, int currentMonth);

    @Query(value = "select * from attendance_table where emp_id=? and date >= CURDATE() - INTERVAL 7 DAY" ,
            nativeQuery = true)
    List<Attendance> findLast7DaysAttendanceStatusByEmpId(String empId);

    boolean existsByEmployeeAndDateAndStatusIn(Employee employee, LocalDate today, List<AttendanceStatus> statuses);
}
