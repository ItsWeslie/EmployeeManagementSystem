package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.enums.SalaryStatus;

public interface SalaryStatusCount {
    SalaryStatus getStatus();
    Integer getSalaryStatusCount();
}
