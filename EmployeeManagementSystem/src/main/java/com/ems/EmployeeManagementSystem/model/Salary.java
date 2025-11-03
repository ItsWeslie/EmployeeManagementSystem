package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.Month;
import java.time.Year;



@Entity
@Data
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id",nullable=false)
    @JsonIgnore
    private Employee employee;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Month month;

    @Column(nullable = false)
    private Year year;

    @Enumerated(EnumType.STRING)
    private SalaryStatus status=SalaryStatus.PENDING;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate paymentDate;
}
