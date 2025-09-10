package com.ems.EmployeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalaryRequestDTO {


    private int id;

    private String emp_id;

    private double basicPay;

    private double hra;

    private double allowances;

    private double deductions;

    private LocalDate paymentDate;

}
