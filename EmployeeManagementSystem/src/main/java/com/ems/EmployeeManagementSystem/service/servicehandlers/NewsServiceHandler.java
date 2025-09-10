package com.ems.EmployeeManagementSystem.service.servicehandlers;

import com.ems.EmployeeManagementSystem.model.News;
import com.ems.EmployeeManagementSystem.repository.NewsRepo;
import com.ems.EmployeeManagementSystem.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceHandler {

    private final NewsRepo newsRepo;
    private final NewsService newsService;

    public ResponseEntity<List<News>> getNews() {
        List<News> news = newsRepo.findAll();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    public ResponseEntity<?> addNews(News news) {
        News savedNews = newsRepo.save(news);
        boolean isNewsAssigned = newsService.initializeNewsStatusForEmployees(savedNews);

        if(isNewsAssigned) {
            return new ResponseEntity<>(news, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News not created");
    }

    public ResponseEntity<String> updateNews(int newsId, News news) {
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

    public ResponseEntity<String> deleteNews(int newsId) {
        News news = newsRepo.findById(newsId).orElse(null);
        if (news!=null) {
            newsRepo.delete(news);
            return new ResponseEntity<>("News deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
    }
}
