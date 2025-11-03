package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.enums.Gender;
import com.ems.EmployeeManagementSystem.model.enums.MaritalStatus;
import com.ems.EmployeeManagementSystem.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
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
        List<EmployeeResponseDTO> dtoList = new ArrayList<>();

        for (Employee emp : employees) {
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setId(emp.getId());
            dto.setEmpId(emp.getEmpId());
            dto.setName(emp.getName());
            dto.setEmail(emp.getEmail());
            dto.setPhone(emp.getPhone());
            dto.setUserName(emp.getUserName());
            dto.setRole(emp.getRole());
            dto.setAddress(emp.getAddress());
            dto.setDepartment(emp.getDepartment());
            dto.setBloodGroup(emp.getBloodGroup());
            dto.setCity(emp.getCity());
            dto.setState(emp.getState());
            dto.setDob(emp.getDob());
            dto.setGender(emp.getGender());
            dto.setFatherName(emp.getFatherName());
            dto.setJoinDate(emp.getJoinDate());
            dto.setNationality(emp.getNationality());
            dto.setMaritalStatus(emp.getMaritalStatus());
            dto.setSpouseName(emp.getSpouseName());
            dto.setWorkLocation(emp.getWorkLocation());

            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<EmployeeResponseDTO> employeeResponse(Employee emp) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(emp.getId());
        dto.setEmpId(emp.getEmpId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setPhone(emp.getPhone());
        dto.setUserName(emp.getUserName());
        dto.setRole(emp.getRole());
        dto.setAddress(emp.getAddress());
        dto.setDepartment(emp.getDepartment());
        dto.setBloodGroup(emp.getBloodGroup());
        dto.setCity(emp.getCity());
        dto.setState(emp.getState());
        dto.setDob(emp.getDob());
        dto.setGender(emp.getGender());
        dto.setFatherName(emp.getFatherName());
        dto.setJoinDate(emp.getJoinDate());
        dto.setNationality(emp.getNationality());
        dto.setMaritalStatus(emp.getMaritalStatus());
        dto.setSpouseName(emp.getSpouseName());
        dto.setImageName(emp.getProfilePic().getImageName());
        dto.setImageType(emp.getProfilePic().getImageType());
        dto.setImageData(emp.getProfilePic().getImageData());
        dto.setWorkLocation(emp.getWorkLocation());

        return ResponseEntity.ok(dto);
    }

}
