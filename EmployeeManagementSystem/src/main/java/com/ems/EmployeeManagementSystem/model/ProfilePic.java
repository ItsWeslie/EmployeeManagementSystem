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
@Table(name = "profile_pic")
public class ProfilePic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id",nullable = false)
    private Employee employee;

    private String imageName;
    private String imageType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

}
