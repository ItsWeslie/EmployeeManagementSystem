package com.ems.EmployeeManagementSystem.exceptionHandling;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDate timestamp;
}
