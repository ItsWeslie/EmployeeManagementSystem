package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Gender;
import com.ems.EmployeeManagementSystem.model.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmployeeRequestDTO {

    private String empId;
    private String name;
    @Email(message = "Invalid email id format")
    private String email;
    @Pattern(regexp = "^(\\+91[- ]?)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    private String userName;
    private String password;
    private String role;
    private String department;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String city;
    private String state;
    private String nationality;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String bloodGroup;
    private String fatherName;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private String spouseName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate joinDate;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
    private String workLocation;

}
