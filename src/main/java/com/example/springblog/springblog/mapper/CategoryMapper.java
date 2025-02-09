package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ArticleDTO;
import com.example.springblog.springblog.dto.CategoryDTO;
import com.example.springblog.springblog.model.Category;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        if (category.getArticles() != null) {
            categoryDTO.setArticles(category.getArticles().stream().map(article -> {
                ArticleDTO articleDTO = new ArticleDTO();
                articleDTO.setId(article.getId());
                articleDTO.setTitle(article.getTitle());
                articleDTO.setContent(article.getContent());
                articleDTO.setUpdatedAt(article.getUpdatedAt());
                articleDTO.setCategoryName(article.getCategory().getName());
                return articleDTO;

            }).collect(Collectors.toList()));
        };
        return categoryDTO;
    };
}
