package com.ems.EmployeeManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "employee_news_status",
        uniqueConstraints = @UniqueConstraint(columnNames = {"emp_id", "news_id"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNewsStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "is_read", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false,referencedColumnName = "emp_id")
    private Employee employee;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id",nullable = false, referencedColumnName = "newsId")
    private News news;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
