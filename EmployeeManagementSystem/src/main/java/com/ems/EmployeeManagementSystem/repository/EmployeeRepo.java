package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Employee;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmpId(String empId);

    Optional<Employee> findByEmail(String email);

    @Query(value = "select emp_id from employees where emp_id=?;",nativeQuery = true)
    String findEmployeeByEmpId(String empId);

    Employee findRoleByEmail(@Email String email);

    String findEmployeeRoleByEmail(@Email String email);
}
