package com.ems.EmployeeManagementSystem.mappers;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.ProfilePic;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements Function<EmployeeRequestDTO, Employee> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee apply(EmployeeRequestDTO dto) {

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
