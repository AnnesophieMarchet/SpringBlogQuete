package com.example.springblog.springblog.service;

import com.example.springblog.springblog.dto.ImageDTO;
import com.example.springblog.springblog.mapper.ImageMapper;
import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.model.Image;
import com.example.springblog.springblog.repository.ArticleRepository;
import com.example.springblog.springblog.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ArticleRepository articleRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ArticleRepository articleRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
        this.imageMapper = imageMapper;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();

        return images.stream()
                .map(imageMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImageDTO getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.map(imageMapper::convertToDTO).orElse(null);
    }

    public ImageDTO createImage(ImageDTO imageDTO) {
        Image image = new Image();
        image.setUrl(imageDTO.getUrl());

        if (imageDTO.getArticleIds() != null && !imageDTO.getArticleIds().isEmpty()) {
            List<Article> articles = articleRepository.findAllById(imageDTO.getArticleIds());
            image.setArticles(articles);
        }

        Image savedImage = imageRepository.save(image);
        return imageMapper.convertToDTO(savedImage);
    }

    public ImageDTO updateImage(Long id, ImageDTO imageDTO) {
        Optional<Image> existingImage = imageRepository.findById(id);
        if (existingImage.isPresent()) {
            Image image = existingImage.get();
            image.setUrl(imageDTO.getUrl());

            if (imageDTO.getArticleIds() != null) {
                List<Article> articles = articleRepository.findAllById(imageDTO.getArticleIds());
                image.setArticles(articles);
            } else {
                image.getArticles().clear();  // Si aucune liste d'articles, on vide la liste
            }

            Image updatedImage = imageRepository.save(image);
            return imageMapper.convertToDTO(updatedImage);
        }
        return null;
    }

    public boolean deleteImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            Image img = image.get();

            // Détacher l'image des articles
            if (img.getArticles() != null && !img.getArticles().isEmpty()) {
                for (Article article : img.getArticles()) {
                    article.getImages().remove(img);
                }
                articleRepository.saveAll(img.getArticles());
            }

            imageRepository.delete(img);
            return true;
        }
        return false;
    }


}
