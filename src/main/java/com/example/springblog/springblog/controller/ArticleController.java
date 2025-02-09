package com.example.springblog.springblog.controller;
import com.example.springblog.springblog.service.ArticleService;
import com.example.springblog.springblog.dto.ArticleDTO;
import com.example.springblog.springblog.model.*;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }




    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
     List<ArticleDTO> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        ArticleDTO savedArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDetails);
        if (updatedArticle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (articleService.deleteArticle(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/search-title")
//    public ResponseEntity <List<ArticleDTO>> getArticleByTitle(@RequestParam String searchTitle) {
//       List<Article> articles = articleRepository.findByTitle(searchTitle);
//        if (articles.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
//        return ResponseEntity.ok(articleDTOs);
//    }
//
//    // Méthode pour liste d'articles dont le contenu contient une chaine de caractère fournie en paramètre
//    @GetMapping("/search/content")
//    public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam("content") String content) {
//        List<Article> articles = articleRepository.findByContentContaining(content);
//        if (articles.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
//
//        return ResponseEntity.ok(articleDTOs);
//    }
//
//
//
//    // Méthode pour  des articles créés après une certaine date
//    @GetMapping("/search/createdAfter")
//    public ResponseEntity<List<ArticleDTO>> getArticlesCreatedAfter(@RequestParam("date") String date) {
//        LocalDateTime createdAt = LocalDateTime.parse(date);
//        List<Article> articles = articleRepository.findByCreatedAtAfter(createdAt);
//
//        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
//        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
//    }
//
//    // Méthode  5 derniers articles créés
//    @GetMapping("/latest")
//    public ResponseEntity<List<ArticleDTO>> getFiveLastArticles() {
//        List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
//        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
//        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
//    }


}
