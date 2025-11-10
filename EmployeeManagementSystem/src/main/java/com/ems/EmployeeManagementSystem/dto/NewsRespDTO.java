package com.ems.EmployeeManagementSystem.dto;

import com.ems.EmployeeManagementSystem.enums.NewsTag;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRespDTO {


    private long newsId;

    private String newsTitle;

    private String newsContent;

    private NewsTag newsTag;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate newsDate;

    private boolean isRead;

}
