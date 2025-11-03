package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
}
