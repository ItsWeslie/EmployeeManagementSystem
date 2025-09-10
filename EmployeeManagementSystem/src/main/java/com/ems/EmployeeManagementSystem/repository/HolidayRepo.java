package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HolidayRepo extends JpaRepository<Holidays,Integer> {
    boolean existsHolidayByDate(LocalDate date);
}
