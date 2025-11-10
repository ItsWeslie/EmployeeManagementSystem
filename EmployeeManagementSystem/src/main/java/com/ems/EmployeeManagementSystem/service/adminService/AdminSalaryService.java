package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.AdminSalaryResponseDTO;
import com.ems.EmployeeManagementSystem.dto.SalaryRequestDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.EmployeeNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.NextPaymentDateNotFoundException;
import com.ems.EmployeeManagementSystem.exceptionHandling.SalaryAlreadyExistException;
import com.ems.EmployeeManagementSystem.exceptionHandling.SalaryNotFoundException;
import com.ems.EmployeeManagementSystem.interfaces.AdminSalaryServiceIF;
import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.enums.SalaryStatus;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryAllotmentDateRepo;
import com.ems.EmployeeManagementSystem.repository.SalaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSalaryService implements AdminSalaryServiceIF {

    private final EmployeeRepo employeeRepo;
    private final SalaryRepo salaryRepo;
    private final SalaryAllotmentDateRepo salaryAllotmentDateRepo;

    public double calculateTotalSalary(double basicPay,double hra,double allowances, double deduction)
    {
        return basicPay + hra + allowances + deduction;
    }

    private Salary isSalaryExist(String empId)
    {
        return salaryRepo.findSalaryByEmployee_empId(empId).orElse(null);
    }

    private List<SalaryInsights> findTotalSalaryPaidCurrentMonthInPercentage() {
        return salaryRepo.totalSalaryPaidCurrentMonthInPercentage();
    }

    private List<SalaryStatusCount> getCountOfSalaryStatus() {
        return salaryRepo.getSalaryStatusCount();
    }

    private int findTotalEmployees() {
        return salaryRepo.getTotalEmployees();
    }

    private HighestAndLowestEarningDepartment findHighestAndLowestEarningDepartment() {
        return salaryRepo.findHighestAndLowestEarningDepartment();
    }

    private TotalSalaryPaidThisMonthAndYear  calculateTotalSalaryPaidThisYearAndCurrentMonth() {
        Month currentMonth = LocalDate.now().getMonth();
        return salaryRepo.findTotalSalaryPaidThisMonthAndYear(currentMonth.name(), Year.now());
    }

    private LocalDate getNextPaymentDate()
    {
        return salaryAllotmentDateRepo.getSalaryAllotmentDate()
                .orElseThrow(()->
                        new NextPaymentDateNotFoundException("Next payment date not found, Please try again later!"));
    }

    @Override
    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    public ResponseEntity<Salary> addSalary(SalaryRequestDTO salaryRequestDTO) {


        String empId = salaryRequestDTO.getEmpId();

        Salary isSalaryExist = isSalaryExist(empId);

        if(isSalaryExist!=null)
        {
            throw new SalaryAlreadyExistException("Salary Already Exist for empId : "+empId);
        }

        Employee employee = employeeRepo.findByEmpId(empId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found for this empId : "+empId));

        double totalSalary = calculateTotalSalary(
                salaryRequestDTO.getBasicPay()
                ,salaryRequestDTO.getHra()
                ,salaryRequestDTO.getAllowances()
                ,salaryRequestDTO.getDeductions());

        Month currentMonth = LocalDate.now().getMonth();

        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setName(employee.getName());
        salary.setDepartment(employee.getDepartment());
        salary.setTotalSalary(totalSalary);
        salary.setBasicPay(salaryRequestDTO.getBasicPay());
        salary.setHra(salaryRequestDTO.getHra());
        salary.setAllowances(salaryRequestDTO.getAllowances());
        salary.setDeductions(salaryRequestDTO.getDeductions());
        salary.setMonth(currentMonth);
        salary.setYear(Year.now());
        salary.setPaymentDate(salaryRequestDTO.getPaymentDate());

        salaryRepo.save(salary);

        return ResponseEntity.status(HttpStatus.CREATED).body(salary);
    }

    @Override
    @Transactional(rollbackFor = SalaryNotFoundException.class)
    public ResponseEntity<String> approveSalary(int salaryId) {
        Salary salary = salaryRepo.findById(salaryId)
                .orElseThrow(()->new SalaryNotFoundException("Salary not found for the id : "+salaryId));

        if(!salary.getStatus().equals(SalaryStatus.PENDING))
        {
            return ResponseEntity.badRequest().body("Salary is not pending");
        }

        salary.setStatus(SalaryStatus.PAID);
        salaryRepo.save(salary);

        return ResponseEntity.ok("Salary approved successfully for Salary id : "+salaryId);
    }

    @Override
    @Transactional(rollbackFor = SalaryNotFoundException.class)
    public ResponseEntity<String> updateSalary(SalaryRequestDTO salaryRequestDTO) {

        int salaryId = salaryRequestDTO.getId();

        Salary salary = salaryRepo.findById(salaryId)
                .orElseThrow(()->new SalaryNotFoundException("Salary not found for the id : "+salaryId));

        double totalSalary = calculateTotalSalary(
                salaryRequestDTO.getBasicPay()
                ,salaryRequestDTO.getHra()
                ,salaryRequestDTO.getAllowances()
                ,salaryRequestDTO.getDeductions());

        salary.setTotalSalary(totalSalary);
        salary.setBasicPay(salaryRequestDTO.getBasicPay());
        salary.setHra(salaryRequestDTO.getHra());
        salary.setAllowances(salaryRequestDTO.getAllowances());
        salary.setDeductions(salaryRequestDTO.getDeductions());

        salaryRepo.save(salary);

        return ResponseEntity.ok("Salary updated successfully for salary id : "+salaryId);
    }

    @Override
    @Transactional(rollbackFor = SalaryNotFoundException.class)
    public ResponseEntity<String> deleteSalary(int salaryId) {

        if(!salaryRepo.existsById(salaryId))
        {
            throw new SalaryNotFoundException("Salary not found for salaryId : "+salaryId);
        }

        salaryRepo.deleteById(salaryId);

        return ResponseEntity.ok("Salary deleted successfully for salaryId : "+salaryId);
    }

    @Override
    public ResponseEntity<AdminSalaryResponseDTO> getSalaryDash() {

        List<Salary> salaries = salaryRepo.findAll();

        TotalSalaryPaidThisMonthAndYear totalSalaryPaidThisMonthAndYear
                = calculateTotalSalaryPaidThisYearAndCurrentMonth();

        HighestAndLowestEarningDepartment highestAndLowestEarningDepartment
                = findHighestAndLowestEarningDepartment();

        int totalEmployees = findTotalEmployees();

        List<SalaryStatusCount> salaryStatusCounts = getCountOfSalaryStatus();

        List<SalaryInsights> totalSalaryPaidCurrentMonthInPercentage = findTotalSalaryPaidCurrentMonthInPercentage();

        LocalDate nextPaymentDate = getNextPaymentDate();

        AdminSalaryResponseDTO adminSalaryResponseDTO = AdminSalaryResponseDTO.builder()
                .totalSalaryPaidThisMonthAndYear(totalSalaryPaidThisMonthAndYear)
                .totalEmployees(totalEmployees)
                .totalSalaryPaidCurrentMonthInPercentage(totalSalaryPaidCurrentMonthInPercentage)
                .nextPaymentDate(nextPaymentDate)
                .salaryList(salaries)
                .salaryStatusCount(salaryStatusCounts)
                .highestAndLowestEarningDepartment(highestAndLowestEarningDepartment)
                .build();

        return ResponseEntity.ok(adminSalaryResponseDTO);
    }

}
