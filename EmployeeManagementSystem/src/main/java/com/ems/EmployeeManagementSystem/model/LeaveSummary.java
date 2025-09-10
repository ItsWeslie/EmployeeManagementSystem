package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name="leave_summary_table")
public class LeaveSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaveSum_id",updatable = false)
    private int id;

    @Column(name = "remaining_leave")
    private int remainingLeave=40;

    @Column(name="leave_taken")
    private int leaveTaken=0;

    @Column(name = "total_leave")
    private int totalLeave=0;

    @Column(name="total_leaves_allowed")
    private int totalLeavesAllowed=40;

    private int longestLeaveDuration;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;


    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id", nullable = false)
    @JsonIgnore
    @ManyToOne
    private Employee employee;



}
