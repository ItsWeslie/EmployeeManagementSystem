package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.exceptionHandling.ResourceNotFound;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.EmployeeNewsStatus;
import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.repository.EmployeeNewsStatusRepo;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    // this service still not tested
    private final EmployeeRepo employeeRepo;
    private final EmployeeNewsStatusRepo employeeNewsStatusRepo;

    public boolean initializeNewsStatusForEmployees(News newNEWS)
    {
        List<Employee> employees = employeeRepo.findAll();

        List<EmployeeNewsStatus> statuses = employees
                .stream()
                .map(employee -> {
            EmployeeNewsStatus newsStatus = new EmployeeNewsStatus();
            newsStatus.setEmployee(employee);
            newsStatus.setNews(newNEWS);
            newsStatus.setRead(false);
            return newsStatus;}).toList();

            employeeNewsStatusRepo.saveAll(statuses);

            return true;
    }

//    public void markAsRead(String emp_id,int news_id)
//    {
//        EmployeeNewsStatus employeeNewsStatus = employeeNewsStatusRepo.findByEmployee_EmpIdAndNews_NewsId(emp_id,news_id)
//                .orElseThrow(()-> new ResourceNotFound("News status not found for Employee ID: "
//                        + emp_id + " and News ID: " + news_id));
//
//        if(!employeeNewsStatus.isRead()) {
//            employeeNewsStatus.setRead(true);
//            employeeNewsStatus.setReadAt(LocalDateTime.now());
//        }
//        employeeNewsStatusRepo.save(employeeNewsStatus);
//    }

}
