package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.EmployeeSalaryInsights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeSalaryInsightsRepo extends JpaRepository<EmployeeSalaryInsights, Long> {

    Optional<EmployeeSalaryInsights> findEmployeeSalaryInsightsByEmployee_empId(String empId);
}
