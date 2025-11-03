package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.dto.Last7DaysAttendanceDTO;
import com.ems.EmployeeManagementSystem.model.Attendance;
import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.ems.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    @Query(value = "select * from attendance_table where emp_id=? and date = ?", nativeQuery = true)
    Attendance findByEmpIdAndDate(String empId, LocalDate date);

    @Query(value = "select count(status) from attendance_table " +
            "where emp_id=? " +
            "and " +
            "month=? " +
            "and status = 'PRESENT';" ,nativeQuery = true)
    int countPresentByEmpIdAndMonth(String empId, int currentMonth);

    @Query(value = "select * from attendance_table where emp_id=? and date >= CURDATE() - INTERVAL 7 DAY" ,
            nativeQuery = true)
    List<Attendance> findLast7DaysAttendanceStatusByEmployee_EmpId(String empId);

    boolean existsByEmployeeAndDateAndStatusIn(Employee employee, LocalDate today, List<AttendanceStatus> statuses);

    Optional<List<Attendance>> getAttendanceByEmployee_empIdAndYear(String empId, Year year);
}
