package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ArticleAuthorDTO;
import com.example.springblog.springblog.dto.ArticleCreateDTO;
import com.example.springblog.springblog.dto.ArticleDTO;
import com.example.springblog.springblog.dto.AuthorDTO;
import com.example.springblog.springblog.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
    public class ArticleMapper {

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }
        if (article.getImages() != null) {
            articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
        if (article.getArticleAuthors() != null) {
            articleDTO.setAuthors(article.getArticleAuthors().stream()
                    .filter(articleAuthor -> articleAuthor.getAuthor() != null)
                    .map(articleAuthor -> {
                        ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
                        articleAuthorDTO.setArticleId(article.getId());
                        articleAuthorDTO.setAuthorId(articleAuthor.getAuthor().getId());
                        articleAuthorDTO.setContribution(articleAuthor.getContribution());
                        articleAuthorDTO.setFirstname(articleAuthor.getAuthor().getFirstname());
                        articleAuthorDTO.setLastname(articleAuthor.getAuthor().getLastname());
                        return articleAuthorDTO;
                    })
                    .collect(Collectors.toList()));
        }


        return articleDTO;
    }
    // Méthode pour convertir ArticleCreateDTO en Article
    public Article convertToEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();

        // Convertir les champs simples
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());

        // Convertir la catégorie (en utilisant l'ID de la catégorie, supposons que tu as une méthode pour la récupérer)
        if (articleCreateDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(articleCreateDTO.getCategoryId()); // Cette étape suppose que l'entité Category existe déjà en base de données
            article.setCategory(category);
        }

        // Convertir les images (si elles sont présentes dans le DTO)
        if (articleCreateDTO.getImages() != null) {
            List<Image> images = articleCreateDTO.getImages().stream().map(imageDTO -> {
                Image image = new Image();
                image.setUrl(imageDTO.getUrl()); // Assure-toi que l'URL est correctement validée dans ton DTO
                return image;
            }).collect(Collectors.toList());
            article.setImages(images);
        }

        // Convertir les auteurs (s'ils sont présents dans le DTO)
        if (articleCreateDTO.getAuthors() != null) {
            List<ArticleAuthor> articleAuthors = articleCreateDTO.getAuthors().stream().map(authorDTO -> {
                ArticleAuthor articleAuthor = new ArticleAuthor();
                articleAuthor.setContribution(authorDTO.getContribution());

                Author author = new Author();
                author.setId(authorDTO.getAuthorId()); // Supposons que tu as une méthode pour récupérer l'author à partir de son ID
                articleAuthor.setAuthor(author);

                return articleAuthor;
            }).collect(Collectors.toList());
            article.setArticleAuthors(articleAuthors);
        }

        return article;
    }
}


