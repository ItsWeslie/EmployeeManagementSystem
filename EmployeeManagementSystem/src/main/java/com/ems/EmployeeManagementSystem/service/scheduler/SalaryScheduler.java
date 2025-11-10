package com.ems.EmployeeManagementSystem.service.scheduler;


import com.ems.EmployeeManagementSystem.exceptionHandling.SalaryNotFoundException;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.EmployeeSalaryInsights;
import com.ems.EmployeeManagementSystem.model.Salary;
import com.ems.EmployeeManagementSystem.model.SalaryAllotmentDate;
import com.ems.EmployeeManagementSystem.enums.SalaryStatus;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.EmployeeSalaryInsightsRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryAllotmentDateRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryScheduler {


    private final SalaryRepo salaryRepo;
    private final EmployeeRepo employeeRepo;
    private final SalaryAllotmentDateRepo salaryAllotmentDateRepo;
    private final EmployeeSalaryInsightsRepo empSalaryInsightsRepo;

    public int calculateTaxAmount(double totalSalary)
    {
        double annualSalary = totalSalary * 12;

        double baseValue = annualSalary/300000;
        int percent = (int)baseValue*5;
        int tax = 300000%percent;
        return tax/12;
    }

    public double calculateNetSalary(double basicPay,double hra,double allowances,double deduction,double tax)
    {
        return basicPay+hra+allowances-deduction-tax;
    }

    @Scheduled(cron = "0 0 0 1 * *") // Every 1st day of the month at 12 AM
    public void scheduleNextSalaryDate() {
        LocalDate nextPaymentDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        Year currentYear = Year.now();
        SalaryAllotmentDate salaryAllotmentDate = new SalaryAllotmentDate();
        salaryAllotmentDate.setNextPaymentDate(nextPaymentDate);
        salaryAllotmentDate.setYear(currentYear);
        salaryAllotmentDateRepo.save(salaryAllotmentDate);
    }

    @Scheduled(cron = "0 0 0 1-10 * *")
    public void scheduleSalary()
    {
        LocalDate paymentDate = LocalDate.now();
        Optional<LocalDate> existingPaymentDate =  salaryAllotmentDateRepo.getSalaryAllotmentDate();

        if(existingPaymentDate.isPresent())
        {
            if(existingPaymentDate.get().equals(paymentDate))
            {
                List<Employee> employees = employeeRepo.findAll();

                for(Employee employee : employees)
                {
                    Salary newSalary = new Salary();
                    Salary previousSalary  = getSalaryDetails(employee.getEmpId());

                    newSalary.setEmployee(employee);
                    newSalary.setTotalSalary(previousSalary.getTotalSalary());
                    newSalary.setBasicPay(previousSalary.getBasicPay());
                    newSalary.setHra(previousSalary.getHra());
                    newSalary.setAllowances(previousSalary.getAllowances());
                    newSalary.setDeductions(previousSalary.getDeductions());
                    newSalary.setMonth(LocalDate.now().getMonth());
                    newSalary.setYear(Year.now());
                    newSalary.setStatus(SalaryStatus.PENDING);
                    newSalary.setPaymentDate(existingPaymentDate.get());
                    salaryRepo.save(newSalary);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    private void salaryInsights()
    {
        List<Salary> currentMonthSalaries = salaryRepo
                .findSalariesByMonthAndStatus(LocalDate.now().getMonth(),SalaryStatus.PAID);

        for(Salary salary : currentMonthSalaries)
        {
            int taxAmount = calculateTaxAmount(salary.getTotalSalary());

            double netSalary  =
                    calculateNetSalary(salary.getBasicPay(),salary.getHra(),salary.getAllowances(),salary.getDeductions(),taxAmount);

            double monthlyAvg = salaryRepo
                    .findMonthlyAverageSalaryByEmployee_empId(salary.getEmployee().getEmpId());

            double lastSixMonthSalaryAverage = salaryRepo
                    .findLastSixMonthSalaryAverageByEmployee_empId(salary.getEmployee().getEmpId());

            EmployeeSalaryInsights employeeSalaryInsights = EmployeeSalaryInsights.builder()
                    .employee(salary.getEmployee())
                    .netSalary(netSalary)
                    .avgMonthlySalary(monthlyAvg)
                    .tax(taxAmount)
                    .last6MonthAvgSalary(lastSixMonthSalaryAverage)
                    .month(salary.getMonth())
                    .year(salary.getYear())
                    .build();

            empSalaryInsightsRepo.save(employeeSalaryInsights);
        }
    }

    private Salary getSalaryDetails(String empId)
    {
        return  salaryRepo
                .findSalaryByEmployee_empIdAndMonthAndYear(empId,LocalDate.now().getMonth().minus(1),Year.now())
                .orElseThrow(()-> new SalaryNotFoundException("Salary not found for previous month"));
    }

}
