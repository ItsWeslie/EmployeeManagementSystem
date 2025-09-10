package com.ems.EmployeeManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int newsId;

    @Column(nullable = false)
    private String newsTitle;

    @Column(nullable = false)
    private String newsContent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsTag newsTag;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy")
    private LocalDate newsDate;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EmployeeNewsStatus> employeeStatuses;
}
