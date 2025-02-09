package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ArticleAuthorDTO;
import com.example.springblog.springblog.dto.ArticleDTO;
import com.example.springblog.springblog.dto.AuthorDTO;
import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.model.Image;
import org.springframework.stereotype.Component;

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
}


