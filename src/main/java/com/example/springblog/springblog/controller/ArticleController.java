package com.example.springblog.springblog.controller;
import com.example.springblog.springblog.dto.ArticleDTO;
import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.model.Category;
import com.example.springblog.springblog.model.Image;
import com.example.springblog.springblog.repository.ArticleRepository;
import com.example.springblog.springblog.repository.CategoryRepository;
import com.example.springblog.springblog.repository.ImageRepository;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {


    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    private ArticleDTO convertToDto(Article article){
       ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null){
            articleDTO.setCategoryName(article.getCategory().getName());
        }
        if(article.getImages() != null){
            articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
        return articleDTO;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getAllArticleById(@PathVariable Long id) {
     Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDto(article));
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        if(article.getCategory() != null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            article.setCategory(category);
        }
        if (article.getImages() != null && !article.getImages().isEmpty()) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : article.getImages()) {
                if (image.getId() != null) {
                    // Vérification des images existantes
                    Image existingImage = imageRepository.findById(image.getId()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return ResponseEntity.badRequest().body(null);
                    }
                } else {
                    // Création de nouvelles images
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        }


        Article newArticle = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(newArticle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        Article articleToUpdate = articleRepository.findById(id).orElse(null);
        if (articleToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        articleToUpdate.setTitle(articleDetails.getTitle());
        articleToUpdate.setContent(articleDetails.getContent());
        articleToUpdate.setUpdatedAt(LocalDateTime.now());

        if(articleDetails.getCategory() != null) {
            Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            articleToUpdate.setCategory(category);
        }

        if (articleDetails.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                if (image.getId() != null) {
                    // Vérification des images existantes
                    Image existingImage = imageRepository.findById(image.getId()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return ResponseEntity.badRequest().build(); // Image non trouvée, retour d'une erreur
                    }
                } else {
                    // Création de nouvelles images
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            // Mettre à jour la liste des images associées
            articleToUpdate.setImages(validImages);
        } else {
            // Si aucune image n'est fournie, on nettoie la liste des images associées
            articleToUpdate.getImages().clear();
        }
      Article updateArticle =  articleRepository.save(articleToUpdate);
        return ResponseEntity.ok(convertToDto(updateArticle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-title")
    public ResponseEntity <List<ArticleDTO>> getArticleByTitle(@RequestParam String searchTitle) {
       List<Article> articles = articleRepository.findByTitle(searchTitle);
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    // Méthode pour liste d'articles dont le contenu contient une chaine de caractère fournie en paramètre
    @GetMapping("/search/content")
    public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam("content") String content) {
        List<Article> articles = articleRepository.findByContentContaining(content);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());

        return ResponseEntity.ok(articleDTOs);
    }



    // Méthode pour  des articles créés après une certaine date
    @GetMapping("/search/createdAfter")
    public ResponseEntity<List<ArticleDTO>> getArticlesCreatedAfter(@RequestParam("date") String date) {
        LocalDateTime createdAt = LocalDateTime.parse(date);
        List<Article> articles = articleRepository.findByCreatedAtAfter(createdAt);

        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
    }

    // Méthode  5 derniers articles créés
    @GetMapping("/latest")
    public ResponseEntity<List<ArticleDTO>> getFiveLastArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDto).collect(Collectors.toList());
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
    }

}
