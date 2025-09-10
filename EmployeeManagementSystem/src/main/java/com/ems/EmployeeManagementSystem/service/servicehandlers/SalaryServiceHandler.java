package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.dto.SalaryRequestDTO;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Salary;
import com.ems.EmployeeManagementSystem.service.helpers.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryServiceHandler {

    private final ServiceHelper serviceHelper;


    public double calculateNetSalary(double basicPay,double hra,double allowances,double deduction,double tax)
    {
        return basicPay+hra+allowances-deduction-tax;
    }

    public double calculateTotalSalary(double basicPay,double hra,double allowances, double deduction)
    {
        return basicPay + hra + allowances + deduction;

    }

    public int calculateTaxAmount(double totalSalary)
    {
        double annualSalary = totalSalary * 12;

        double baseValue = annualSalary/300000;
        int percent = (int)baseValue*5;
        int tax = 300000%percent;
        return tax/12;

    }


    public ResponseEntity<String> addSalary(SalaryRequestDTO salaryRequestDTO) {
        String emp_id = salaryRequestDTO.getEmp_id();
        Employee employee = serviceHelper.isValidEmployee(emp_id);

        double basicPay = salaryRequestDTO.getBasicPay();
        double hra = salaryRequestDTO.getHra();
        double allowance = salaryRequestDTO.getAllowances();
        double deduction = salaryRequestDTO.getDeductions();
        double totalSalary = calculateTotalSalary(basicPay,hra,allowance,deduction);
        double tax = calculateTaxAmount(totalSalary);
        double netSalaryOfYear = calculateNetSalary(basicPay,hra,allowance,deduction,tax*12);
        // remaining

        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setBasicPay(basicPay);
        salary.setHra(hra);
        salary.setAllowances(allowance);
        salary.setDeductions(deduction);
        salary.setPaymentDate(salaryRequestDTO.getPaymentDate());
        salary.setTotalSalary(totalSalary);
        //salary.setNetSalary();

        // remaining

        return ResponseEntity.ok("Salary added successfully");
    }

}
