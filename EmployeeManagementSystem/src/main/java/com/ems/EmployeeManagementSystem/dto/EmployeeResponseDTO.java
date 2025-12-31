package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.enums.Gender;
import com.ems.EmployeeManagementSystem.enums.MaritalStatus;
import com.ems.EmployeeManagementSystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class EmployeeResponseDTO {

    private long id;
    private String empId;
    private String name;
    private String email;
    private String phone;
    private String userName;
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

    public ResponseEntity<List<EmployeeResponseDTO>> employeeResponse(List<Employee> employees) {
        List<EmployeeResponseDTO> dtoList = employees.stream()
                .map(employee -> EmployeeResponseDTO.builder()
                        .id(employee.getId())
                        .empId(employee.getEmpId())
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .phone(employee.getPhone())
                        .userName(employee.getUserName())
                        .role(employee.getRole())
                        .address(employee.getAddress())
                        .department(employee.getDepartment())
                        .bloodGroup(employee.getBloodGroup())
                        .city(employee.getCity())
                        .state(employee.getState())
                        .dob(employee.getDob())
                        .gender(employee.getGender())
                        .fatherName(employee.getFatherName())
                        .joinDate(employee.getJoinDate())
                        .nationality(employee.getNationality())
                        .maritalStatus(employee.getMaritalStatus())
                        .spouseName(employee.getSpouseName())
                        .workLocation(employee.getWorkLocation())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<EmployeeResponseDTO> employeeResponse(Employee emp) {

        EmployeeResponseDTO dto = EmployeeResponseDTO.builder()
                .id(emp.getId())
                .empId(emp.getEmpId())
                .name(emp.getName())
                .email(emp.getEmail())
                .phone(emp.getPhone())
                .userName(emp.getUserName())
                .role(emp.getRole())
                .address(emp.getAddress())
                .department(emp.getDepartment())
                .bloodGroup(emp.getBloodGroup())
                .city(emp.getCity())
                .state(emp.getState())
                .dob(emp.getDob())
                .gender(emp.getGender())
                .fatherName(emp.getFatherName())
                .joinDate(emp.getJoinDate())
                .nationality(emp.getNationality())
                .maritalStatus(emp.getMaritalStatus())
                .spouseName(emp.getSpouseName())
                .imageName(emp.getProfilePic().getImageName())
                .imageType(emp.getProfilePic().getImageType())
                .imageData(emp.getProfilePic().getImageData())
                .workLocation(emp.getWorkLocation())
                .build();

        return ResponseEntity.ok(dto);
    }

}
