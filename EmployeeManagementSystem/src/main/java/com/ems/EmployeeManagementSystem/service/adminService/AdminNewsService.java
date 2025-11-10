package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.NewsRespDTO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        News news1 = newsRepo.findById(newsId).orElse(null);
        if(news1!=null) {
            news1.setNewsTitle(news.getNewsTitle());
            news1.setNewsContent(news.getNewsContent());
            news1.setNewsDate(news.getNewsDate());
            news1.setNewsTag(news.getNewsTag());
            newsRepo.save(news1);
            return new ResponseEntity<>("News updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteNews(long newsId) {
        News news = newsRepo.findById(newsId).orElse(null);
        if (news!=null) {
            newsRepo.delete(news);
            return new ResponseEntity<>("News deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<NewsRespDTO>> getNews(String empId) {

        List<News> news = newsRepo.findAll();

        List<EmployeeNewsStatus> newsStatuses = employeeNewsStatusRepo.findEmployeeNewsStatusesByEmployee_EmpId(empId);

        Map<Long,Boolean> empNewsStsMp = new HashMap<>();

        for(EmployeeNewsStatus newsStatus : newsStatuses) {
            empNewsStsMp.put(newsStatus.getNews().getNewsId(), newsStatus.isRead());
        }

        List<NewsRespDTO> newsResp = new ArrayList<>();

        for(News newNews:news)
        {
            NewsRespDTO newsRespDTO = new NewsRespDTO();
            newsRespDTO.setNewsId(newNews.getNewsId());
            newsRespDTO.setNewsTitle(newNews.getNewsTitle());
            newsRespDTO.setNewsContent(newNews.getNewsContent());
            newsRespDTO.setNewsDate(newNews.getNewsDate());
            newsRespDTO.setNewsTag(newNews.getNewsTag());
            newsRespDTO.setRead(empNewsStsMp.getOrDefault(newNews.getNewsId(), false));
            newsResp.add(newsRespDTO);
        }

        return ResponseEntity.ok(newsResp);

    }
}
