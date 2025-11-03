package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.*;
import com.ems.EmployeeManagementSystem.model.enums.SalaryStatus;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, Integer> {

    Salary findTopByEmployeeOrderByYearDescMonthDesc(Employee employee);

    @Query(value = "select * from salary_table where emp_id= :emp_id limit 1",nativeQuery = true)
    Optional<Salary> findSalaryByEmployee_empId(@Param("emp_id") String empId);

    boolean existsById(int salary_id);

    void deleteById(int salary_id);

    Optional<Salary> findSalaryByEmployee_empIdAndMonthAndYear(String empId, Month month, Year year);

    List<Salary> findSalariesByMonthAndStatus(Month month, SalaryStatus status);

    double findMonthlyAverageSalaryByEmployee_empId(String empId);

    double findLastSixMonthSalaryAverageByEmployee_empId(String empId);

    @Query(value = "select status,round(sum(total_salary)*100/(select sum(total_salary) from salary_table),2) as percentage " +
            "from salary_table " +
            "group by status",nativeQuery = true)
    List<SalaryInsights> totalSalaryPaidCurrentMonthInPercentage();

    @Query(value="select status,count(*) as salaryStatusCount from salary_table group by status",nativeQuery = true)
    List<SalaryStatusCount> getSalaryStatusCount();

    @Query(value = "SELECT \n" +
            "    (SELECT department \n" +
            "     FROM salary_table \n" +
            "     GROUP BY department \n" +
            "     ORDER BY SUM(total_salary) DESC \n" +
            "     LIMIT 1) AS highest_earning_department,\n" +
            "    \n" +
            "    (SELECT department \n" +
            "     FROM salary_table \n" +
            "     GROUP BY department \n" +
            "     ORDER BY SUM(total_salary) ASC \n" +
            "     LIMIT 1) AS lowest_earning_department;\n",nativeQuery = true)
    HighestAndLowestEarningDepartment findHighestAndLowestEarningDepartment();

    @Query(value="select count(distinct emp_id) as total_employees from salary_table",nativeQuery = true)
    int getTotalEmployees();

    @Query(value = "select" +
            "(select sum(total_salary) from salary_table where month= :month) as total_salary_paid_this_month," +
            "(select sum(total_salary) from salary_table where year= :year) as total_salary_paid_this_year;"
            ,nativeQuery = true)
    TotalSalaryPaidThisMonthAndYear findTotalSalaryPaidThisMonthAndYear(@Param("month") String month, @Param("year") Year year);


}
