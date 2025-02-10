package com.example.springblog.springblog.mapper;

import com.example.springblog.springblog.dto.ImageDTO;
import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.model.Image;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ImageMapper {

    public ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        if (image.getArticles() != null) {
            imageDTO.setArticleIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return imageDTO;
    }

    public Image convertToEntity(ImageDTO imageDTO) {
        Image image = new Image();
        image.setUrl(imageDTO.getUrl());

        return image;
    }

}