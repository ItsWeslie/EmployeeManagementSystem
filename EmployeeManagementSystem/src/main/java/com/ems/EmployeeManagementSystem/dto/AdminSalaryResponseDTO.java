package com.ems.EmployeeManagementSystem.dto;


import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSalaryResponseDTO {

    private TotalSalaryPaidThisMonthAndYear totalSalaryPaidThisMonthAndYear;
    private HighestAndLowestEarningDepartment highestAndLowestEarningDepartment;
    private int totalEmployees;
    private List<SalaryStatusCount> salaryStatusCount;
    private List<SalaryInsights> totalSalaryPaidCurrentMonthInPercentage;
    private List<Salary> salaryList;
    private LocalDate nextPaymentDate;
}
