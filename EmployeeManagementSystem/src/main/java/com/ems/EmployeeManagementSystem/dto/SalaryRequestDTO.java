package com.ems.EmployeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryRequestDTO {

    private String empId;

    private int id;

    private double basicPay;

    private double hra;

    private double allowances;

    private double deductions;

    private LocalDate paymentDate;

}
