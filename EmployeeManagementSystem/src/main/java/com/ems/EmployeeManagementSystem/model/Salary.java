package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.Year;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salary_table",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"emp_id", "month", "year"})
})
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id",updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id",nullable=false)
    @JsonIgnore
    private Employee employee;

    @Column(nullable = false)
    private double totalSalary;

    @Column(nullable = false)
    private double basicPay;

    @Column(nullable = false)
    private double hra;

    @Column(nullable = false)
    private double allowances;

    @Column(nullable = false)
    private double deductions;

    @Column(nullable = false)
    private double netSalary;

    @Column(nullable = false)
    private double avgMonthlySalary;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private double netVsGrossRatio;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private Year year;

    @Enumerated(EnumType.STRING)
    private SalaryStatus status=SalaryStatus.PENDING;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate nextSalaryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate paymentDate;
}
