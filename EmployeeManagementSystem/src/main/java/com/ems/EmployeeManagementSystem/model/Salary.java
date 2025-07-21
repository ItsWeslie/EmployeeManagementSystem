package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salary_table")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id",updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id",nullable=false)
    @JsonIgnore
    private Employee employee;
}
