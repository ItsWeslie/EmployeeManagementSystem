package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.ProfilePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePicRepo extends JpaRepository<ProfilePic, Long> {

    Optional<ProfilePic> findProfilePicByEmployee_EmpId(String empId);
}
