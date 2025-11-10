package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.service.adminService.AdminNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminNewsController{

    private final AdminNewsService adminNewsService;

    @GetMapping("/getNews")
    public ResponseEntity<List<News>> getNews() {
        return adminNewsService.getNews();
    }

    @PostMapping("/addNews")
    public ResponseEntity<?> addNews(@RequestBody News news) {
        return adminNewsService.addNews(news);
    }

    @PutMapping("/updateNews/{newsId}")
    public ResponseEntity<String> updateNews(@RequestBody News news,@PathVariable("newsId") long newsId) {
        return adminNewsService.updateNews(newsId,news);
    }

    @DeleteMapping("/deleteNews/{newsId}")
    public ResponseEntity<String> deleteNews(@PathVariable("newsId") long newsId) {
        return adminNewsService.deleteNews(newsId);
    }
}
