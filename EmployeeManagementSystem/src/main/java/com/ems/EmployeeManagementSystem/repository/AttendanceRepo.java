package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
}
