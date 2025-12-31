package com.ems.EmployeeManagementSystem.service.employeeService;

import com.ems.EmployeeManagementSystem.dto.EmployeeSalaryResponseDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.*;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.repository.EmployeeSalaryInsightsRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryAllotmentDateRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryRepo;
import com.ems.EmployeeManagementSystem.interfaces.EmployeeSalaryServiceIF;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class EmployeeSalaryService implements EmployeeSalaryServiceIF {

    private final SalaryRepo salaryRepo;
    private final EmployeeSalaryInsightsRepo empSalaryInsightsRepo;
    private final SalaryAllotmentDateRepo salaryAllotmentDateRepo;


    private Salary getCurrentMonthSalary(String empId,Month currentMonth,Year year)
    {
        System.out.println("Current Month: "+currentMonth);
        return salaryRepo.findSalaryByEmployee_empIdAndMonthAndYear(empId,currentMonth,year)
                .orElseThrow(()->new SalaryNotFoundException("Current month salary not found for the empId : "+empId));
    }

    private EmployeeSalaryInsights getSalaryInsights(String empId)
    {
        return empSalaryInsightsRepo.findEmployeeSalaryInsightsByEmployee_empId(empId)
                .orElseThrow(()->new SalaryInsightsNotFoundException("SalaryInsights not found for the empId : "+empId));
    }

    private LocalDate getNextPaymentDate()
    {
        return salaryAllotmentDateRepo.getSalaryAllotmentDate()
                .orElseThrow(()->
                        new NextPaymentDateNotFoundException("Next payment date not found, Please try again later!"));
    }

    @Override
    @Transactional
    public ResponseEntity<EmployeeSalaryResponseDTO> getMySalaryDetails(String empId) {

        Month currentMonth = LocalDate.now().getMonth();

        Salary currentMonthSalary = getCurrentMonthSalary(empId,currentMonth,Year.now());

        EmployeeSalaryInsights employeeSalaryInsights = getSalaryInsights(empId);

        LocalDate nextSalaryDate = getNextPaymentDate();

        EmployeeSalaryResponseDTO empSalaryResponseDTO = EmployeeSalaryResponseDTO.builder()
                        .totalSalary(currentMonthSalary.getTotalSalary())
                        .basicPay(currentMonthSalary.getBasicPay())
                        .HRA(currentMonthSalary.getHra())
                        .allowances(currentMonthSalary.getAllowances())
                        .deductions(currentMonthSalary.getDeductions())
                        .netSalary(employeeSalaryInsights.getNetSalary())
                        .avgMonthlySalary(employeeSalaryInsights.getAvgMonthlySalary())
                        .tax(employeeSalaryInsights.getTax())
                        .last6MonthAvgSalary(employeeSalaryInsights.getAvgMonthlySalary())
                        .nextSalaryDate(nextSalaryDate)
                        .build();

        return ResponseEntity.ok(empSalaryResponseDTO);
    }
}
