package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.NewsResponseDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.NewsNotFoundException;
import com.ems.EmployeeManagementSystem.interfaces.AdminNewsServiceIF;
import com.ems.EmployeeManagementSystem.model.EmployeeNewsStatus;
import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.repository.EmployeeNewsStatusRepo;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import com.ems.EmployeeManagementSystem.service.employeeService.EmployeeNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminNewsService implements AdminNewsServiceIF {

    private final NewsRepo newsRepo;
    private final EmployeeNewsService newsService;
    private final EmployeeNewsStatusRepo employeeNewsStatusRepo;

    public ResponseEntity<List<News>> getNews() {
        return ResponseEntity.ok(newsRepo.findAll());
    }

    public ResponseEntity<?> addNews(News news) {
        News savedNews = newsRepo.save(news);
        boolean isNewsAssigned = newsService.initializeNewsStatusForEmployees(savedNews);

        if(isNewsAssigned) {
            return new ResponseEntity<>(news, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News not created");
    }

    public ResponseEntity<String> updateNews(long newsId, News news) {
        News existingNews = newsRepo.findById(newsId)
                .orElseThrow(()-> new NewsNotFoundException("News not found for id: " + newsId));

            existingNews.setNewsTitle(news.getNewsTitle());
            existingNews.setNewsContent(news.getNewsContent());
            existingNews.setNewsDate(news.getNewsDate());
            existingNews.setNewsTag(news.getNewsTag());
            newsRepo.save(existingNews);

            return new ResponseEntity<>("News updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteNews(long newsId) {

        newsRepo.findById(newsId)
                .orElseThrow(()-> new NewsNotFoundException("News not found for id: " + newsId));

            newsRepo.deleteById(newsId);
            return new ResponseEntity<>("News deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<NewsResponseDTO>> getNews(String empId) {

        List<EmployeeNewsStatus> newsStatuses = employeeNewsStatusRepo
                .findEmployeeNewsStatusesByEmployee_EmpId(empId);

        Map<Long,Boolean> empNewsStsMap = new HashMap<>();

        newsStatuses.forEach(newsStatus ->
            empNewsStsMap.put(newsStatus.getNews().getNewsId(), newsStatus.isRead()));

        List<NewsResponseDTO> newsResponse = newsRepo.findAll()
                .stream()
                .map(news -> NewsResponseDTO.builder()
                        .newsId(news.getNewsId())
                        .newsTitle(news.getNewsTitle())
                        .newsContent(news.getNewsContent())
                        .newsDate(news.getNewsDate())
                        .newsTag(news.getNewsTag())
                        .isRead(empNewsStsMap.getOrDefault(news.getNewsId(), false))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(newsResponse);
    }
}
