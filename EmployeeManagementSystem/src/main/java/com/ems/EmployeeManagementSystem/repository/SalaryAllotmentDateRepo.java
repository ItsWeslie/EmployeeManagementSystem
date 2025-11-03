package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.SalaryAllotmentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface SalaryAllotmentDateRepo extends JpaRepository<SalaryAllotmentDate, Long> {

    @Query(value = "select next_payment_date from salary_allotment_date_table" +
            " where year = Year(now()) and month(next_payment_date) = month(date_add(curdate(),interval 1 month))" +
            "order by next_payment_date limit 1 ",nativeQuery = true)
    Optional<LocalDate> getSalaryAllotmentDate();
}
