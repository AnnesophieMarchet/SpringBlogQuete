package com.example.springblog.springblog.controller;


import com.example.springblog.springblog.dto.ImageDTO;
import com.example.springblog.springblog.model.Article;
import com.example.springblog.springblog.model.Image;
import com.example.springblog.springblog.repository.ArticleRepository;
import com.example.springblog.springblog.repository.ImageRepository;
import com.example.springblog.springblog.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> imageDTOs = imageService.getAllImages();
        return ResponseEntity.ok(imageDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO imageDTO = imageService.getImageById(id);
        if (imageDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageDTO);
    }

//    @PostMapping
//    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
//        Image savedImage = imageRepository.save(image);
//        return ResponseEntity.status(201).body(convertToDTO(savedImage));
//    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        ImageDTO savedImageDTO = imageService.createImage(imageDTO);
        return ResponseEntity.status(201).body(savedImageDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        ImageDTO updatedImageDTO = imageService.updateImage(id, imageDTO);
        if (updatedImageDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedImageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody Image imageDetails) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        image.setUrl(imageDetails.getUrl());
//        Image updatedImage = imageRepository.save(image);
//        return ResponseEntity.ok(convertToDTO(updatedImage));
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        imageRepository.delete(image);
//        return ResponseEntity.noContent().build();
//    }
//
//    // ✅ Détacher une image des articles avant suppression
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
//        Image image = imageRepository.findById(id).orElse(null);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // On enlève l'image des articles avant de la supprimer
//        if (image.getArticles() != null && !image.getArticles().isEmpty()) {
//            for (Article article : image.getArticles()) {
//                article.getImages().remove(image);
//            }
//            articleRepository.saveAll(image.getArticles()); // On sauvegarde les articles modifiés
//        }
//
//        imageRepository.delete(image);
//        return ResponseEntity.noContent().build();
//    }



}
