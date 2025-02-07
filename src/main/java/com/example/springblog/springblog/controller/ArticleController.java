package com.example.springblog.springblog.controller;

import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {


    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getAllArticleById(@PathVariable Long id) {
     Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        Article newArticle = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        Article articleToUpdate = articleRepository.findById(id).orElse(null);
        if (articleToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        articleToUpdate.setTitle(article.getTitle());
        articleToUpdate.setContent(article.getContent());
        articleToUpdate.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(articleToUpdate);
        return ResponseEntity.ok(articleToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-title")
    public ResponseEntity <List<Article>> getArticleByTitle(@RequestParam String searchTitle) {
       List<Article> articles = articleRepository.findByTitle(searchTitle);
        if (articles == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }

    // Méthode pour liste d'articles dont le contenu contient une chaine de caractère fournie en paramètre
    @GetMapping("/search/content")
    public ResponseEntity<List<Article>> getArticlesByContent(@RequestParam("content") String content) {
        List<Article> articles = articleRepository.findByContentContaining(content);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }



    // Méthode pour  des articles créés après une certaine date
    @GetMapping("/search/createdAfter")
    public ResponseEntity<List<Article>> getArticlesCreatedAfter(@RequestParam("date") String date) {
        LocalDateTime createdAt = LocalDateTime.parse(date);
        List<Article> articles = articleRepository.findByCreatedAtAfter(createdAt);
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

    // Méthode  5 derniers articles créés
    @GetMapping("/latest")
    public ResponseEntity<List<Article>> getFiveLastArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

}
