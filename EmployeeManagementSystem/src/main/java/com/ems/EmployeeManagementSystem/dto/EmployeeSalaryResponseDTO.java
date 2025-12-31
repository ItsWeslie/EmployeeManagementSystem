package com.ems.EmployeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
