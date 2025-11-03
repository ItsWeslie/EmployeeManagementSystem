package com.ems.EmployeeManagementSystem.controller.controllerIF;

import com.ems.EmployeeManagementSystem.model.News;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface NewsControllerIF {

    public ResponseEntity<?> addNews(@RequestBody News news);
    public ResponseEntity<String> updateNews(@RequestBody News news,@PathVariable("newsId") long newsId);
    public ResponseEntity<String> deleteNews(@PathVariable("newsId") long newsId);
    public ResponseEntity<List<News>> getNews();
}
