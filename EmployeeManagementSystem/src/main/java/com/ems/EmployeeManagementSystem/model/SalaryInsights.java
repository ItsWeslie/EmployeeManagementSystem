package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;

public interface SalaryInsights {
    SalaryStatus getStatus();
    Double getPercentage();
}
