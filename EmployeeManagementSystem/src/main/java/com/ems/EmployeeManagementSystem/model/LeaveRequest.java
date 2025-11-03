package com.ems.EmployeeManagementSystem.model;


import com.ems.EmployeeManagementSystem.model.enums.LeaveStatus;
import com.ems.EmployeeManagementSystem.model.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leave_request_table")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leaveReq_id",updatable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "emp_id", nullable=false,name = "emp_id")
    @JsonIgnore
    private Employee employee;

    @Column(nullable=false)
    @NotNull(message = "Leave start date is required!")
    private LocalDate startDate;

    @Column(nullable=false)
    @NotNull(message = "Leave end date is required!")
    private LocalDate endDate;

    @Column(nullable=false)
    @NotNull(message = "Reason is required!")
    @Size(min = 5, message = "Reason at least contain 5 characters ")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @NotNull(message = "Required leave type")
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private LeaveStatus status = LeaveStatus.PENDING;

}
