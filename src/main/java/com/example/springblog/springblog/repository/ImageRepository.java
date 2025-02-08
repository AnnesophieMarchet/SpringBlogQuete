package com.example.springblog.springblog.repository;

import com.example.springblog.springblog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
