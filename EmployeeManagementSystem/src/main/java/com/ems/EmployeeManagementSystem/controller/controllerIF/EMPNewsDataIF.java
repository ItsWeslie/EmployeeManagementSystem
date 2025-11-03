package com.ems.EmployeeManagementSystem.controller.controllerIF;

import com.ems.EmployeeManagementSystem.dto.NewsReqDTO;
import com.ems.EmployeeManagementSystem.dto.NewsRespDTO;
import com.ems.EmployeeManagementSystem.model.News;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EMPNewsDataIF {
    public ResponseEntity<List<NewsRespDTO>> getNews(@PathVariable("empId")String empId);
    public void markNewsAsRead(@RequestBody NewsReqDTO newsReqDTO);
    public void markAllNewsAsRead(@PathVariable("empId") String empId);
}
