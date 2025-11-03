package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "salary_allotment_date_table")
public class SalaryAllotmentDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,name = "nextPayment_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate nextPaymentDate;

    @Column(nullable = false)
    private Year year;

}
