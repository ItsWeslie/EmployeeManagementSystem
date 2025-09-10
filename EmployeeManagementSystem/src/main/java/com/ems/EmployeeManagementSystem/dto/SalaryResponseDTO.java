package com.ems.EmployeeManagementSystem.dto;


import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.SalaryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalaryResponseDTO {


    private int id;

    private double totalSalary;

    private double basicPay;

    private double hra;

    private double allowances;

    private double deductions;

    private double netSalary;

    private double avgMonthlySalary;

    private double tax;

    private double netGrossRatio;

    private int month;

    private Year year;

    private SalaryStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate nextSalaryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate paymentDate;
}
