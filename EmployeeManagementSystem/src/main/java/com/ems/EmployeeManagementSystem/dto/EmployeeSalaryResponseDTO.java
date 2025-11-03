package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Salary;
import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryResponseDTO {

    private double totalSalary;
    private double basicPay;
    private double HRA;
    private double allowances;
    private double deductions;
    private double netSalary;
    private double avgMonthlySalary;
    private double tax;
    private double last6MonthAvgSalary;
    private LocalDate nextSalaryDate;
}
