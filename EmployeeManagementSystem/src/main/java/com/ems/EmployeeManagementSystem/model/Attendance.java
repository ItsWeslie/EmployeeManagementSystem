package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
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
}
