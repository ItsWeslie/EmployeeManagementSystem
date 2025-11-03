package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmpId(String empId);

    Optional<Employee> findByEmail(String email);

    @Query(value = "select emp_id from employees where emp_id=?;",nativeQuery = true)
    String findEmployeeByEmpId(String empId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE employees e SET e.password= :password WHERE emp_id = :empId",nativeQuery = true)
    void updateEmployeePassword(@Param("empId") String empId,@Param("password") String newPassword);

    @Transactional
    @Query(value = "select e.password from employees e where emp_id= :empId",nativeQuery = true)
    Optional<String> findEmployeePasswordByEmpId(@Param("empId") String empId);
}
