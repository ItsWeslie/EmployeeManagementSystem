package com.ems.EmployeeManagementSystem.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicRespDTO {


    private String imageName;
    private String imageType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;
}
