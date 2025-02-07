package com.example.springblog.springblog.repository;

import com.example.springblog.springblog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findByTitle(String title);
    List<Article> findByContentContaining(String content);
    List<Article> findByCreatedAtAfter(LocalDateTime createdAt);
    List<Article> findTop5ByOrderByCreatedAtDesc();
}
