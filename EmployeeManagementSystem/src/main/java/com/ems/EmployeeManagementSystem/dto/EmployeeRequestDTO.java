package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.enums.Gender;
import com.ems.EmployeeManagementSystem.enums.MaritalStatus;
import com.ems.EmployeeManagementSystem.enums.Role;
import com.ems.EmployeeManagementSystem.model.ProfilePic;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDTO {

    private String empId;
    private String name;
    @Email(message = "Invalid email id format")
    private String email;
    @Pattern(regexp = "^(\\+91[- ]?)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
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
    private PasswordEncoder passwordEncoder;

    @org.springframework.beans.factory.annotation.Autowired
    public EmployeeRequestDTO(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Employee employeeRequest(EmployeeRequestDTO dto) {

        return Employee.builder()
                        .empId(dto.getEmpId())
                                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .address(dto.getAddress())
                .department(dto.getDepartment())
                .bloodGroup(dto.getBloodGroup())
                .city(dto.getCity())
                .state(dto.getState())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .fatherName(dto.getFatherName())
                .joinDate(dto.getJoinDate())
                .nationality(dto.getNationality())
                .maritalStatus(dto.getMaritalStatus())
                .spouseName(dto.getSpouseName())
                .workLocation(dto.getWorkLocation())
                .profilePic(
                        ProfilePic.builder()
                                .imageName(dto.getImageName())
                                .imageType(dto.getImageType())
                                .imageData(dto.getImageData())
                                .build()
                )
                .build();
    }

}
