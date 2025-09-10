package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Gender;
import com.ems.EmployeeManagementSystem.model.MaritalStatus;
import com.ems.EmployeeManagementSystem.model.Role;
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


    public Employee employeeRequest(EmployeeRequestDTO dto) {
        Employee emp = new Employee();
        emp.setEmpId(dto.getEmpId());
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setUserName(dto.getUserName());
        //passwordEncoder().encode(); have to enable after security got enabled;
        emp.setPassword(dto.getPassword());
        emp.setRole(dto.getRole());
        emp.setAddress(dto.getAddress());
        emp.setDepartment(dto.getDepartment());
        emp.setBloodGroup(dto.getBloodGroup());
        emp.setCity(dto.getCity());
        emp.setState(dto.getState());
        emp.setDob(dto.getDob());
        emp.setGender(dto.getGender());
        emp.setFatherName(dto.getFatherName());
        emp.setJoinDate(dto.getJoinDate());
        emp.setNationality(dto.getNationality());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setSpouseName(dto.getSpouseName());
        emp.setImageName(dto.getImageName());
        emp.setImageType(dto.getImageType());
        emp.setImageData(dto.getImageData());
        emp.setWorkLocation(dto.getWorkLocation());
        return emp;
    }

}
