package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.EmployeeNewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeNewsStatusRepo extends JpaRepository<EmployeeNewsStatus, Long> {

    //List<EmployeeNewsStatus> findByEmpId(String empId);
    //Optional<EmployeeNewsStatus> findByEmployee_EmpIdAndNews_NewsId(String empId, int newsId);
    //long countByEmpIdAndReadFalse(String empId);

}
