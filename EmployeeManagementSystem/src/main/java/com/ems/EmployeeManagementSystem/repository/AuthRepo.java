package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepo extends JpaRepository<UserAuth,Long> {

    @Query(value = "select * from user_auth where email=?",nativeQuery = true)
    UserAuth findUserAuthByEmail(String email);
}
