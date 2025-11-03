package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.EmployeeNewsStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeNewsStatusRepo extends JpaRepository<EmployeeNewsStatus, Long> {
    List<EmployeeNewsStatus> findEmployeeNewsStatusesByEmployee_EmpId(String employeeEmpId);

    Optional<EmployeeNewsStatus> findByEmployee_EmpIdAndNews_NewsId(String empId, long newsId);
    //long countByEmpIdAndReadFalse(String empId);

}
