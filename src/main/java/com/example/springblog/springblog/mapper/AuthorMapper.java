package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ArticleAuthorDTO;
import com.example.springblog.springblog.dto.AuthorDTO;
import com.example.springblog.springblog.model.Author;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public AuthorDTO convertToDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstname(author.getFirstname());
        authorDTO.setLastname(author.getLastname());
        authorDTO.setArticleAuthors(author.getArticleAuthors().stream().map(articleAuthor -> {
            ArticleAuthorDTO articleAuthorDTO = new ArticleAuthorDTO();
            articleAuthorDTO.setArticleId(articleAuthor.getArticle().getId());
            articleAuthorDTO.setAuthorId(articleAuthor.getAuthor().getId());
            articleAuthorDTO.setContribution(articleAuthor.getContribution());
            return articleAuthorDTO;
        }).collect(Collectors.toList()));
        return authorDTO;
    }
}
