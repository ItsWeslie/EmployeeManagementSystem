package com.ems.EmployeeManagementSystem.service;


import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Salary;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryScheduler {


    private final SalaryRepo salaryRepo;
    private final EmployeeRepo employeeRepo;


    @Scheduled(cron = "0 0 0 1 * *") // Every 1st day of the month at 12 AM
    public void scheduleNextSalaryDate() {
        LocalDate nextMonthFirstDay = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        List<Employee> employees = employeeRepo.findAll();

        for (Employee employee : employees) {
            Salary salary = salaryRepo.findTopByEmployeeOrderByYearDescMonthDesc(employee);
            if (salary != null && !nextMonthFirstDay.equals(salary.getNextSalaryDate())) {
                salary.setNextSalaryDate(nextMonthFirstDay);
                salaryRepo.save(salary);
            }
        }

        System.out.println("Next salary dates scheduled for employees: " + nextMonthFirstDay);
    }


}
