package com.example.springblog.springblog.repository;

import com.example.springblog.springblog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
