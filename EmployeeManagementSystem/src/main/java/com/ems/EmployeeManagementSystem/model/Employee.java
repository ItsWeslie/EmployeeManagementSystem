package com.ems.EmployeeManagementSystem.model;


import com.ems.EmployeeManagementSystem.model.enums.Gender;
import com.ems.EmployeeManagementSystem.model.enums.MaritalStatus;
import com.ems.EmployeeManagementSystem.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @NotBlank(message = "EmpId must not be empty")
    @Column(name = "emp_id", nullable = false, unique = true)
    private String empId;

    @NotBlank(message = "Name must not be empty")
    @Column(nullable = false)
    private String name;

    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format")
    @NotBlank(message = "Email must not be empty")
    @Column(nullable = false,unique = true)
    private String email;

    @Pattern(regexp = "\\d{10}")
    @NotBlank(message = "Phone must not be empty")
    @Column(nullable = false,unique = true)
    private String phone;


    @Column(nullable = false,unique = true)
    private String userName;

    @JsonIgnore
    @Size(min = 8,message = "Password must be at least 8 characters!")
    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp =  "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,}$",
    message = "Password must be at least 8 characters long and contain at least one uppercase letter, " +
            "one lowercase letter, one number, and one special character")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role must not be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String department;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String address;

    private String city;
    private String state;
    private String nationality;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String fatherName;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus=MaritalStatus.Single;

    private String spouseName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate joinDate;

    private String workLocation;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ProfilePic profilePic;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LeaveRequest> leaves;

    @OneToMany(mappedBy = "employee" ,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Attendance> attendances;

    @OneToMany(mappedBy ="employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Salary> salaries;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LeaveSummary> leaveSummaries;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EmployeeNewsStatus> newsStatuses;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dob=" + dob +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", spouseName='" + spouseName + '\'' +
                ", joinDate=" + joinDate +
                ", workLocation='" + workLocation+"}";
    }
}
