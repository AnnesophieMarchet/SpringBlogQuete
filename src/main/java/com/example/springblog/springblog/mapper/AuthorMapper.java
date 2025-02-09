package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ArticleAuthorDTO;
import com.example.springblog.springblog.dto.AuthorDTO;
import com.example.springblog.springblog.model.Author;

import java.util.stream.Collectors;

public class AuthorMapper {

    private AuthorDTO convertToDTO(Author author){
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
