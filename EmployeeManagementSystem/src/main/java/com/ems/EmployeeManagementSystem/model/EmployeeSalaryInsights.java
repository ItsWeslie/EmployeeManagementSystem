package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.time.Year;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee_salary_insights")
public class EmployeeSalaryInsights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id",nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private double netSalary;

    @Column(nullable = false)
    private double avgMonthlySalary;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private double last6MonthAvgSalary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(nullable = false)
    private Year year;

}
