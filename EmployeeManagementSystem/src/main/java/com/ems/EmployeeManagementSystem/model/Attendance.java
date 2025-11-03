package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.model.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance_table")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id",updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id",nullable = false)
    @JsonIgnore
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status=AttendanceStatus.ABSENT;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private Year year;
}
