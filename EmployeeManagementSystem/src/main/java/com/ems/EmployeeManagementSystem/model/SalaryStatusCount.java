package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;

public interface SalaryStatusCount {
    SalaryStatus getStatus();
    Integer getSalaryStatusCount();
}
