package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.enums.SalaryStatus;

public interface SalaryInsights {
    SalaryStatus getStatus();
    Double getPercentage();
}
