package com.ems.EmployeeManagementSystem.interfaces;

import com.ems.EmployeeManagementSystem.model.News;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminNewsServiceIF {
    ResponseEntity<List<News>> getNews();
    ResponseEntity<?> addNews(News news);
    ResponseEntity<String> updateNews(long newsId, News news);
    ResponseEntity<String> deleteNews(long newsId);
}
