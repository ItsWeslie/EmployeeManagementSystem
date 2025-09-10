package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, Integer> {

    Salary findTopByEmployeeOrderByYearDescMonthDesc(Employee employee);
}
